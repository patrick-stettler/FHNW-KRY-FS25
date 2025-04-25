package com.example;

public class App {

    public static void main(String[] args) throws Exception {
        
        RainbowTable rainbowTable = new RainbowTable(7, 2000, 2000);
        rainbowTable.generateRainbowTable();

        String hashwert = "1d56a37fb6b08aa709fe90e12ca59e12";
        String klartext = rainbowTable.crackHash(hashwert);

        System.out.println(klartext);

    }

}
