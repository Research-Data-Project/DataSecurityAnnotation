package org.lsi.usthb.datasecurity.usthb.domain.service;



import org.lsi.usthb.datasecurity.usthb.annotation.DataSecurityLevel;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.CryptoAlgorithm;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.CryptoFactory;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.model.CryptoOperationType;
import org.lsi.usthb.datasecurity.usthb.domain.model.FrequencyType;
import org.lsi.usthb.datasecurity.usthb.domain.model.SecurityLevel;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class LevelSecurityUtility {


    public static  <T extends Object> T applySecurityLevel(T object, CryptoOperationType cryptoOperationType) throws IllegalAccessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // Get all fields annotated with DataSecurityLevel
        Field[] fieldList = object.getClass().getDeclaredFields();
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(DataSecurityLevel.class)) {

                DataSecurityLevel annotation = field.getAnnotation(DataSecurityLevel.class);
                SecurityLevel securityLevel = annotation.level();
                CryptoAlgorithm cryptoAlgorithm = CryptoFactory.getCryptoAlgorithmByLevel(securityLevel);
                if (cryptoAlgorithm != null) {
                    FrequencyType ft = annotation.frequency();
                    field.setAccessible(true);
                    try {
                        String toEncrypt = ((String) field.get(object));
                        switch (cryptoOperationType) {
                            case ENCRYPT:
                                field.set(object, cryptoAlgorithm.encryptData(toEncrypt,ft.getI()));
                                break;
                            case DECRYPT:
                                field.set(object,cryptoAlgorithm.decryptData(toEncrypt,ft.getI()));
                                break;
                        }
                    }catch(ClassCastException e){
                        byte [] toEncrypt = (byte[])field.get(object);
                        switch (cryptoOperationType) {
                            case ENCRYPT:
                                field.set(object, cryptoAlgorithm.encryptData(toEncrypt,ft.getI()));
                                break;
                            case DECRYPT:
                                field.set(object,cryptoAlgorithm.decryptData(toEncrypt,ft.getI()));
                                break;
                        }
                    }

                }
            }
        }
        return object;
    }
}
