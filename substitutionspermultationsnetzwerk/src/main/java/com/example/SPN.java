package com.example;


public class SPN implements Blockkryptosystem {

    // Substitutionspermultationsnetzwerk
    
    private final int R; // Anzahl der Runden
    private final int M; // Anzahl der Bits pro Subblock
    private final int N; // Anzahl der Subblocks pro Block
    private final String K; // Schlüssel
    private final int[] SBOX; // S-Box 
    private final int[] SINV; // inverse S-Box
    private final int[] BETA; // β-Bitpermutation
    private final int BLOCKSIZE; // Blocklänge


    public SPN(int r, int m, int n, String k, int[] sbox, int[] beta){
        this.R = r;
        this.M = m;
        this. N = n;
        this.K = k;
        this.SBOX = sbox;
        this.SINV = generateSINV(SBOX);
        this.BETA = beta;
        this.BLOCKSIZE = N*M;
    }


    // Verschlüsselung

    @Override
    public String encrypt(String plaintext) {
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


    // Entschlüsselung

    @Override
    public String decrypt(String ciphertext) {
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


    // Wortsubstitution

    private String substitute(String state, int[] sx) {
        String substituted = "";
        for (int i = 0; i < state.length(); i += M) {
            String subblock = state.substring(i, i + M);
            int index = Integer.parseInt(subblock, 2);
            substituted += String.format("%" + M + "s", Integer.toBinaryString(sx[index])).replace(' ', '0');
        }
        return substituted;
    }


    // Bitpermutation

    private String permute(String state, boolean isInverse) {
        char[] permuted = new char[state.length()];
        for (int i = 0; i < state.length(); i++) {
            if(isInverse){
                permuted[i] = state.charAt(BETA[i]);
            }
            else {
                permuted[BETA[i]] = state.charAt(i);
            }
        }
        return new String(permuted);
    }


    // Xor

    private String xor(String a, String b) {
        int result = Integer.parseInt(a, 2) ^ Integer.parseInt(b, 2);
        return String.format("%" + BLOCKSIZE + "s", Integer.toBinaryString(result)).replace(' ', '0');
    }


    // Rundenschlüssel

    private String getRoundKey(int round) {
        if(round > R){
            throw new IllegalArgumentException("unzulässige Rundenzahl");
        }
        return K.substring(round * M, round * M + BLOCKSIZE);
    }


    // inverse S-Box

    private int[] generateSINV(int[] sbox) {
        int[] sinv = new int[sbox.length];
        for (int i = 0; i < sbox.length; i++) {
            int value = SBOX[i];
            sinv[value] = i;
        }
        return sinv;
    }


    // Getter-Methoden

    public int getR(){
        return this.R;
    }

    public int getM(){
        return this.M;
    }

    public int getN(){
        return this.N;
    }

    public String getK(){
        return this.K;
    }

    public int[] getSBOX(){
        return this.SBOX;
    }

    public int[] getSINV(){
        return this.SINV;
    }

    public int[] getBETA(){
        return this.BETA;
    }

    public int getBLOCKSIZE(){
        return this.BLOCKSIZE;
    }

}
