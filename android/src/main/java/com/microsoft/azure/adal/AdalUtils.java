package com.microsoft.azure.adal;

import com.microsoft.aad.adal.AuthenticationSettings;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by durgaprasad on 7/3/17.
 */

public class AdalUtils {

    public static void SetupKey() throws NoSuchAlgorithmException,
            InvalidKeySpecException, UnsupportedEncodingException {
        if (AuthenticationSettings.INSTANCE.getSecretKeyData() == null) {
            // use same key for tests
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Constants.SECRET_FACTORY_INSTANCE_ID);
            SecretKey tempkey = keyFactory.generateSecret(new PBEKeySpec(Constants.SECRET_KEY.toCharArray(), Constants.SALT.getBytes(Constants.UTF8_ENCODING), 100, 256));
            SecretKey secretKey = new SecretKeySpec(tempkey.getEncoded(), Constants.ALGORITHM_TYPE);
            AuthenticationSettings.INSTANCE.setSecretKey(secretKey.getEncoded());
        }
    }


}
