package com.example;

public class App {

    public static void main(String[] args) {
        
        String ciphertext = "00000100110100100000101110111000000000101000111110001110011111110110000001010001010000111010000000010011011001110010101110110000";

        Blockkryptosystem spn = new SPN(4, 
                                        4, 
                                        4, 
                                        "00111010100101001101011000111111", 
                                        new int[]{0xE, 0x4, 0xD, 0x1, 0x2, 0xF, 0xB, 0x8, 0x3, 0xA, 0x6, 0xC, 0x5, 0x9, 0x0, 0x7}, 
                                        new int[]{0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15});

       Verschluesselungmodus ctr = new CTR(spn, 16);

       String plaintext = ctr.decrypt(ciphertext);

       System.out.println(binaryToASCII(plaintext));
       
    }

    
    // ASCII-Umwandlung
    
    private static String binaryToASCII(String binary) {
        String text = "";
        for (int i = 0; i < binary.length(); i += 8) {
            int charCode = Integer.parseInt(binary.substring(i, Math.min(i + 8, binary.length())), 2);
            text += ((char) charCode);
        }
        return text;
    }

}
