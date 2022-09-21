package org.lsi.research.datasecurity.domain.crypto.algo.impl;



import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class RSA {



    public RSA() {

    }


    public  final KeyPair keys=getKeys();


    private  KeyPair getKeys()  {
        if (keys == null) {//TODO :: Check if there are stored keys
            try {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(1024);
                return kpg.generateKeyPair();
            }catch (NoSuchAlgorithmException e){
                return null;
            }
        } else return keys;
    }

    public String processData(String message, CryptoOperationType operationType, int keyLength) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            if(operationType.equals(CryptoOperationType.ENCRYPT)){
                cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
                byte[] cipherText = cipher.doFinal(message.getBytes());
                return Base64.getEncoder()
                        .encodeToString(cipherText);
            }else {
                cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
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
