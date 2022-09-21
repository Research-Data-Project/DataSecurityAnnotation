package org.lsi.usthb.datasecurity.usthb.domain.crypto.algo;


import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.AES;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.ECC;
import org.lsi.usthb.datasecurity.usthb.domain.crypto.algo.impl.TwoFish;
import org.lsi.usthb.datasecurity.usthb.domain.model.SecurityLevel;

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
