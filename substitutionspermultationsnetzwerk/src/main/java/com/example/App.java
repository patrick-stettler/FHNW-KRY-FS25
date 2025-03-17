package com.example;

import java.nio.charset.StandardCharsets;

public class App {

    // Substitutionspermultationsnetzwerk
    
    private static final int R = 4;  // Anzahl der Runden
    private static final int M = 4;  // Anzahl der Bits pro Subblock
    private static final int N = 4;  // Anzahl der Subblocks pro Block
    private static final String K = "00111010100101001101011000111111";  // Schlüssel
    private static final int[] SBOX = {0xE, 0x4, 0xD, 0x1, 0x2, 0xF, 0xB, 0x8, 0x3, 0xA, 0x6, 0xC, 0x5, 0x9, 0x0, 0x7}; 
    private static final int[] SINV = {0xE, 0x3, 0x4, 0x8, 0x1, 0xC, 0xA, 0xF, 0x7, 0xD, 0x9, 0x6, 0xB, 0x2, 0x0, 0x5}; 
    private static final int[] BITPERMUTATION = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15}; 
    private static final int BLOCKSIZE = N*M;
    

    public static void main(String[] args) {
        String ciphertext = "00000100110100100000101110111000000000101000111110001110011111110110000001010001010000111010000000010011011001110010101110110000";
        String plaintext = decryptCTR(ciphertext);

        System.out.println("Entschlüsselung (Bitstring): " + plaintext);
        System.out.println("Entschlüsselung (Bitstring ohne Padding): " + removePadding(plaintext));
        System.out.println("Entschlüsselung (Klartext): " + binaryToASCII(removePadding(plaintext)));
    }


    private static String encryptSPN(String plaintext) {
        String y = "";

        // Initialer Weissschritt
        y = xor(plaintext, getRoundKey(0));

        // reguläre Runden
        for(int i = 1; i <= R-1; i++){
            y = substitute(y, SBOX);
            y = permute(y, false);
            y = xor(y, getRoundKey(i));
        }

        // verkürzte Runde
        y = substitute(y, SBOX);
        y = xor(y, getRoundKey(R));
        return y;
    }


    public String decryptSPN(String ciphertext) {
        String x = "";

        // Initialer Rundenschlüssel
        x = xor(ciphertext, getRoundKey(R));
        
        // reguläre Runden
        for (int i = R-1; i > 0; i--) {
            x = substitute(x, SINV);
            x = permute(x, true);
            x = xor(x, permute(getRoundKey(i), false));
        }
        
        // verkürzte Runde
        x = substitute(x, SINV);
        x = xor(x, getRoundKey(0));
        return x;
    }


    private static String substitute(String state, int[] sx) {
        StringBuilder substituted = new StringBuilder();
        for (int i = 0; i < state.length(); i += N) {
            String subblock = state.substring(i, i + N);
            int index = Integer.parseInt(subblock, 2);
            substituted.append(String.format("%4s", Integer.toBinaryString(sx[index])).replace(' ', '0'));
        }
        return substituted.toString();
    }


    private static String permute(String state, boolean isInverse) {
        char[] permuted = new char[state.length()];
        for (int i = 0; i < state.length(); i++) {
            if(isInverse){
                permuted[i] = state.charAt(BITPERMUTATION[i]);
            }
            else {
                permuted[BITPERMUTATION[i]] = state.charAt(i);
            }
            
        }
        return new String(permuted);
    }


    private static String xor(String a, String b) {
        int result = Integer.parseInt(a, 2) ^ Integer.parseInt(b, 2);
        return String.format("%" + BLOCKSIZE + "s", Integer.toBinaryString(result)).replace(' ', '0');
    }


    private static String getRoundKey(int round) {
        if(round > R){
            throw new IllegalArgumentException("unzulässige Rundenzahl");
        }
        return K.substring(round * N, round * N + BLOCKSIZE);
    }


    private static String decryptCTR(String ciphertext) {
        String plaintext = "";
        String nonce = ciphertext.substring(0, BLOCKSIZE);
        
        for (int i = 1; i < ciphertext.length() / BLOCKSIZE; i++) {
            int counter = i-1;
            int result = (int) (Integer.parseInt(nonce, 2) + counter % Math.pow(2, BLOCKSIZE));
            String blockchiffre = String.format("%" + BLOCKSIZE + "s", Integer.toBinaryString(result)).replace(' ', '0');
            String stromchiffre = encryptSPN(blockchiffre);
            String yi = ciphertext.substring(i * BLOCKSIZE, (i+1) * BLOCKSIZE);
            plaintext += xor(yi, stromchiffre);
        }

        return plaintext;
    }
 

    private static String removePadding(String bitstring) {
        return bitstring.substring(0, bitstring.lastIndexOf('1'));
    }

    
    private static String binaryToASCII(String binary) {
        String text = "";
        
        for (int i = 0; i < binary.length(); i += 8) {
            int charCode = Integer.parseInt(binary.substring(i, Math.min(i + 8, binary.length())), 2);
            text += ((char) charCode);
        }

        return text;
    }

}
