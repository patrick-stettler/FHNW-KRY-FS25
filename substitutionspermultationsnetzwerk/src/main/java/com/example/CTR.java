package com.example;


public class CTR implements Verschluesselungmodus{

    // CTR (ranomized counter)

    private final Blockkryptosystem KRYPTOSYSTEM;
    private final int BLOCKSIZE;


    public CTR(Blockkryptosystem kryptosystem, int blocksize){
        this.KRYPTOSYSTEM = kryptosystem;
        this.BLOCKSIZE = blocksize;
    }


    // Entschlüsselung

    @Override
    public String decrypt(String ciphertext) {
        String plaintext = "";
        String nonce = ciphertext.substring(0, BLOCKSIZE);
        
        for (int i = 1; i < ciphertext.length() / BLOCKSIZE; i++) {
            int counter = i-1;
            int result = (int) (Integer.parseInt(nonce, 2) + counter % Math.pow(2, BLOCKSIZE));
            String blockchiffre = String.format("%" + BLOCKSIZE + "s", Integer.toBinaryString(result)).replace(' ', '0');
            String stromchiffre = KRYPTOSYSTEM.encrypt(blockchiffre);
            String yi = ciphertext.substring(i * BLOCKSIZE, (i+1) * BLOCKSIZE);
            plaintext += xor(yi, stromchiffre);
        }

        return removePadding(plaintext);
    }


    // Padding entfernen

    private String removePadding(String bitstring) {
        return bitstring.substring(0, bitstring.lastIndexOf('1'));
    }
 

    // Xor

    private String xor(String a, String b) {
        int result = Integer.parseInt(a, 2) ^ Integer.parseInt(b, 2);
        return String.format("%" + BLOCKSIZE + "s", Integer.toBinaryString(result)).replace(' ', '0');
    }

}
