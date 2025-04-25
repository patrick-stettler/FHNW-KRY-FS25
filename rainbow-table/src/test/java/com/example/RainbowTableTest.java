package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RainbowTableTest {
    
    RainbowTable rainbowTable;


    @Test
    void testRainbowTable1() {
        
        // Given
        rainbowTable = new RainbowTable(7, 10, 1);
        rainbowTable.generateRainbowTable();

        // When
        String password = "0000000";
        String hash = "29c3eea3f305d6b823f562ac4be35217";

        // Then
        assertEquals(rainbowTable.crackHash(hash), password);

    }

    @Test
    void testRainbowTable2() {
        
        // Given
        rainbowTable = new RainbowTable(7, 10, 1);
        rainbowTable.generateRainbowTable();

        // When
        String password = "87inwgn";
        String hash = "12e2feb5a0feccf82a8d4172a3bd51c3";


        // Then
        assertEquals(rainbowTable.crackHash(hash), password);

    }

    @Test
    void testRainbowTable3() {
        
        // Given
        rainbowTable = new RainbowTable(7, 10, 1);
        rainbowTable.generateRainbowTable();

        // When
        String password = "frrkiis";
        String hash = "437988e45a53c01e54d21e5dc4ae658a";

        // Then
        assertEquals(rainbowTable.crackHash(hash), password);

    }

    @Test
    void testRainbowTable4() {
        
        // Given
        rainbowTable = new RainbowTable(7, 10, 1);
        rainbowTable.generateRainbowTable();

        // When
        String password = "dues6fg";
        String hash = "c0e9a2f2ae2b9300b6f7ef3e63807e84";

        // Then
        assertEquals(rainbowTable.crackHash(hash), password);

    }

}
