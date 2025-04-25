package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


public class RainbowTable {

    public final Map<String, String> RAINBOW_TABLE;
    public final char[] CHARSET;
    public final int PASSWORD_LENGTH;
    public final int CHAIN_LENGTH;
    public final int TABLE_SIZE;
    

    public RainbowTable(int passwordLength, int chainLength, int tableSize) {
        RAINBOW_TABLE = new HashMap<>();
        CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        PASSWORD_LENGTH = passwordLength;
        CHAIN_LENGTH = chainLength;
        TABLE_SIZE = tableSize;
    }


    // Rainbow-Table

    public void generateRainbowTable() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rainbow_table.txt"))) {

            writer.write("Startwert, Endwert");

            for (int i = 0; i < TABLE_SIZE; i++) {          // gesamte Tabelle
                
                String startwert = generateStartValue(i);
                String endwert = "";
                String hash = "";
                String reduce = "";
        
                for (int j = 0; j < CHAIN_LENGTH; j++) {    // einzelne Kette
                    
                    if(j == 0){
                        hash = md5(startwert);
                    }

                    else {
                        hash = md5(reduce);
                    }

                    reduce = reduce(hash, j);

                }

                endwert = reduce;
                writer.newLine();
                writer.write(startwert + ", " + endwert);
                RAINBOW_TABLE.put(startwert, endwert);

            }
        }
        catch (IOException e) {

            e.printStackTrace();

        }
    }


    public String generateStartValue(int index) {
        StringBuilder value = new StringBuilder();
        int base = CHARSET.length; // 36
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            value.insert(0, CHARSET[index % base]);
            index /= base;        
        }
        return value.toString();
    }


    public String crackHash(String targetHash) {

        for(int i = CHAIN_LENGTH-1; i >= 0; i--){

            String hash = targetHash;
            String reduce = "";

            for(int j = i; j < CHAIN_LENGTH-1; j++){
                reduce = reduce(hash, j);
                hash = md5(reduce);
            }

            String endwert = reduce(hash, CHAIN_LENGTH-1);
            
            for (Map.Entry<String, String> entry : RAINBOW_TABLE.entrySet()) {
                if (entry.getValue().equals(endwert)) {
                    String startwert = entry.getKey();
                    for (int j = 0; j < CHAIN_LENGTH; j++) {
                        if(j == 0){
                            reduce = startwert;
                        }
                        hash = md5(reduce);
                        if (hash.equals(targetHash)) {
                            return reduce;
                        }
                        reduce = reduce(hash, j);
                    }
                }
            }
        }
        
        return "kein Treffer!";

    }


    // Hashfunktion

    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            return String.format("%032x", new BigInteger(1, digest));
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei MD5-Berechnung", e);
        }
    }


    // Reduktionsfunktion

    public String reduce(String hash, int step) {
        BigInteger H = new BigInteger(hash, 16).add(BigInteger.valueOf(step));
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            BigInteger[] divMod = H.divideAndRemainder(BigInteger.valueOf(CHARSET.length));
            password.insert(0, CHARSET[divMod[1].intValue()]); // Restbetrag
            H = divMod[0]; // Teilung
        }
        return password.toString();
    }

}