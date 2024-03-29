package org.lsi.research.datasecurity.domain.crypto.algo.impl;


import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class AES implements CryptoAlgorithm {


    public static Map<Integer, byte[]> keys = new HashMap<>();

    public static byte[] IV = null;
    static ECC ecc = new ECC();

    public AES() {
    }


    private byte[] getKeys(int i) {
        try {
            if (keys.get(i) == null) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(i);
                SecretKey ks = keyGenerator.generateKey();
                keys.put(i,ecc.encryptData(ks.getEncoded(),192));
                return ks.getEncoded();
            } else {
                return ecc.decryptData(keys.get(i),192);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("here : " + e.getMessage());
            return null;
        }
    }

    public byte[] getIV() {
        if (IV == null) {
            IV = new byte[12];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);
        }
        return IV;
    }

    public String encryptData(String message, int keyLength) {
        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            byte[] messageAsByte = message.getBytes();
            IV = getIV();

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "AES");
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);
            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            // Perform Encryption
            byte[] cipherText = cipher.doFinal(messageAsByte);
            return Base64.getEncoder()
                    .encodeToString(cipherText);


        } catch (InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            return "";
        }

    }


    public byte [] encryptData(byte[] messageAsByte, int keyLength) {
        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            IV = getIV();

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "AES");
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);
            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            // Perform Encryption
            byte[] cipherText = cipher.doFinal(messageAsByte);
            return cipherText;


        } catch (InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }


    public String decryptData(String message, int keyLength) {
        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            byte[] messageAsByte = message.getBytes();

            // Create SecretKeySpec

            SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "AES");

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(message));
            return new String(plainText);


        } catch (InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            return "";
        }

    }


    public byte [] decryptData(byte[] messageAsByte, int keyLength) {
        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            // Create SecretKeySpec

            SecretKeySpec keySpec = new SecretKeySpec(getKeys(keyLength), "AES");

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            byte[] plainText = cipher.doFinal(messageAsByte);
            return plainText;


        } catch (InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }



}
