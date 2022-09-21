package org.lsi.research.datasecurity.domain.crypto.algo.impl;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class BlowFish {


    public final SecretKey key = getKeys();

    public BlowFish() {
    }


    private SecretKey getKeys() {
        String Key = "Something";
        byte[] KeyData = Key.getBytes();
        SecretKeySpec ks = new SecretKeySpec(KeyData, "Blowfish");
        return ks;
    }

    public String processData(String message, CryptoOperationType operationType,int keyLength) {
        try {

            Cipher cipher = Cipher.getInstance("BlowFish");
            if (operationType.equals(CryptoOperationType.ENCRYPT)) {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] cipherText = cipher.doFinal(message.getBytes());
                return Base64.getEncoder()
                        .encodeToString(cipherText);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] plainText = cipher.doFinal(Base64.getDecoder()
                        .decode(message));
                return new String(plainText);
            }

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                NoSuchAlgorithmException | NoSuchPaddingException exception) {
            exception.printStackTrace();
            return "";
        }

    }
}
