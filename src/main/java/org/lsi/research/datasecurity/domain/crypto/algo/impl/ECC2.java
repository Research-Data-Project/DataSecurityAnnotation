package org.lsi.research.datasecurity.domain.crypto.algo.impl;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;


public class ECC2 {


    public ECC2() {
        System.out.println("new ecc");

    }


    public static KeyPair keys = null;


    private KeyPair getKeys() {
        try {
            if (keys == null) {
                Security.addProvider(new BouncyCastleProvider());
                KeyPairGenerator ecKeyGen = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
                ecKeyGen.initialize(new ECGenParameterSpec("brainpoolP384r1"));
                KeyPair ecKeyPair = ecKeyGen.generateKeyPair();
                keys = ecKeyPair;
                return ecKeyPair;
            } else return keys;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            System.out.println("here : " + e.getMessage());
            return null;
        }
    }

    public String encryptData(String message, int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
                cipher.init(Cipher.ENCRYPT_MODE, getKeys().getPublic());
                byte[] cipherText = cipher.doFinal(message.getBytes());
                return Base64.getEncoder()
                        .encodeToString(cipherText);


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException exception) {
            exception.printStackTrace();
            return "";
        }

    }

    public byte [] encryptData(byte[] message, int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
                cipher.init(Cipher.ENCRYPT_MODE, getKeys().getPublic());
                byte[] cipherText = cipher.doFinal(message);
                return cipherText;


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }


    public String decryptData(String message, int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);

                cipher.init(Cipher.DECRYPT_MODE, getKeys().getPrivate());
                byte[] plainText = cipher.doFinal(Base64.getDecoder()
                        .decode(message));
                return new String(plainText);


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException exception) {
            exception.printStackTrace();
            return "";
        }

    }

    public byte [] decryptData(byte[] message, int keyLength) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);

                cipher.init(Cipher.DECRYPT_MODE, getKeys().getPrivate());
                byte[] plainText = cipher.doFinal(message);
                return plainText;


        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException exception) {
            exception.printStackTrace();
            return new byte[0];
        }

    }
}
