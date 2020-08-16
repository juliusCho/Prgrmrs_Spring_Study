package com.github.prgrms.socialserver.global.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

public final class EncryptUtil {

    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;

    private static SecretKeySpec secretKeySpec;

    private static Cipher cipher;

    private static IvParameterSpec ivParameterSpec;



    public static Encryptor setEncryption(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        validation(key);

        byte[] keyBytes = key.getBytes(ENCODING_TYPE);
        secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ivParameterSpec = new IvParameterSpec(keyBytes);

        return new Encryptor();
    }



    private static void validation(String key) {
        Optional.ofNullable(key)
                .filter(x -> !x.isEmpty())
                .filter(x -> x.length() == 16)
                .orElseThrow(IllegalArgumentException::new);
    }




    public static class Encryptor {

        public String encrypt(String str) throws Exception {
            if (str == null || str.isEmpty()) return null;

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(str.getBytes(ENCODING_TYPE));
            return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
        }

        public String decrypt(String str) throws Exception {
            if (str == null || str.isEmpty()) return null;

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decoded = Base64.getDecoder().decode(str.getBytes(ENCODING_TYPE));
            return new String(cipher.doFinal(decoded), ENCODING_TYPE);
        }
    }

}
