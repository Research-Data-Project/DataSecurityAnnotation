package org.lsi.usthb.datasecurity.usthb.domain.crypto.algo;


import org.lsi.usthb.datasecurity.usthb.domain.crypto.model.CryptoOperationType;

public interface CryptoAlgorithm {


    String encryptData(String message, int keyLength);

    byte[] encryptData(byte[] message, int keyLength);


    String decryptData(String message,int keyLength);

    byte[] decryptData(byte[] message,int keyLength);

}
