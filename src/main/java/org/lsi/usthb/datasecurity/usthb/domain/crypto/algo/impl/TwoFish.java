package org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.CryptoAlgorithm;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.model.CryptoOperationType;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TwoFish implements CryptoAlgorithm {


    public static Map<Integer, byte[]> keys = new HashMap<>();

    static ECC ecc = new ECC();

    public TwoFish() {
    }


    private byte[] getKeys(int i) {
        try {
            if (keys.get(i) == null) {
                Security.addProvider(new BouncyCastleProvider());
                KeyGenerator keyGenerator = KeyGenerator.getInstance("twofish");
                keyGenerator.init(i);
                SecretKey ks = keyGenerator.generateKey();
                //keys.put(i,ecc.processData(ks.getEncoded(),CryptoOperationType.ENCRYPT,0));
                keys.put(i, ks.getEncoded());
                return ks.getEncoded();
            } else {
               // return ecc.processData(,CryptoOperationType.ENCRYPT,0);
                return keys.get(i);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("here : " + e.getMessage());
            return null;
        }
    }

    public String encryptData(String message,  int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("twofish");
                SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "twofish");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] cipherText = cipher.doFinal(message.getBytes());
                return Base64.getEncoder()
                        .encodeToString(cipherText);


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                NoSuchAlgorithmException | NoSuchPaddingException exception) {
            exception.printStackTrace();
            return "";
        }

    }
    public byte[] encryptData(byte[] message,  int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("twofish");
                SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "twofish");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] cipherText = cipher.doFinal(message);
                return cipherText;


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                NoSuchAlgorithmException | NoSuchPaddingException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }



    public String decryptData(String message,  int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("twofish");

                SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "twofish");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] plainText = cipher.doFinal(Base64.getDecoder()
                        .decode(message));
                return new String(plainText);


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                NoSuchAlgorithmException | NoSuchPaddingException exception) {
            exception.printStackTrace();
            return "";
        }

    }


    public byte[] decryptData(byte[] message,  int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("twofish");

                SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "twofish");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] plainText = cipher.doFinal(message);
                return plainText;

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                NoSuchAlgorithmException | NoSuchPaddingException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }
}
