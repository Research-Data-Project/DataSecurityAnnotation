package org.lsi.research.datasecurity.domain.crypto.algo;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CryptoFactory {


    public static CryptoAlgorithm getCryptoAlgorithmByLevel(SecurityLevel level) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        switch (level) {
            case HIGHLY_SENSITIVE:
                return new AES();
            case SENSITIVE:
                return new TwoFish();
        }
        return null;
    }
}
