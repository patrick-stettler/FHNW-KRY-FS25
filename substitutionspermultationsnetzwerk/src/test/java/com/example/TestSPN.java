package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestSPN {

    private SPN spn;


    @Test
    void testSPN1() {
        
        // Given
        spn = new SPN(  3,
                        4,
                        3,
                        "000110101111110000000111",
                        new int[]{0b0101, 0b0100, 0b1101, 0b0001, 0b0011, 0b1100, 0b1011, 0b1000, 0b1010, 0b0010, 0b0110, 0b1111, 0b1001, 0b1110, 0b0000, 0b0111},
                        new int[]{4, 5, 8, 9, 0, 1, 10, 11, 2, 3, 6, 7});
        String plaintext = "111101010110";
        String ciphertext = "000101111010";

        // When
        String encyption = spn.encrypt(plaintext);
        String decryption = spn.decrypt(ciphertext);

        // Then
        assertEquals(encyption, ciphertext);
        assertEquals(decryption, plaintext);

    }


    @Test
    void testSPN2() {
        
        // Given
        spn = new SPN(  4,
                        4,
                        4,
                        "00010001001010001000110000000000",
                        new int[]{0xE, 0x4, 0xD, 0x1, 0x2, 0xF, 0xB, 0x8, 0x3, 0xA, 0x6, 0xC, 0x5, 0x9, 0x0, 0x7}, 
                        new int[]{0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15});
        String plaintext = "0001001010001111";
        String ciphertext = "1010111010110100";

        // When
        String encyption = spn.encrypt(plaintext);
        String decryption = spn.decrypt(ciphertext);

        // Then
        assertEquals(encyption, ciphertext);
        assertEquals(decryption, plaintext);

    }

}
