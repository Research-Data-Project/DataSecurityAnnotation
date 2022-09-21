package org.lsi.research.datasecurity.domain.crypto.algo;


public interface CryptoAlgorithm {


    String encryptData(String message, int keyLength);

    byte[] encryptData(byte[] message, int keyLength);


    String decryptData(String message,int keyLength);

    byte[] decryptData(byte[] message,int keyLength);

}
