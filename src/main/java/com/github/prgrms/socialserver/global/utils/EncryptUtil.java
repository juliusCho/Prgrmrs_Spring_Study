package com.github.prgrms.socialserver.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    private static final Charset ENCODING_TYPE = StandardCharsets.UTF_8;

    private static SecretKeySpec secretKeySpec;

    private static Cipher cipher;

    private static IvParameterSpec ivParameterSpec;



    public static Encryptor setEncryption(String key) {
        validation(key);

        try {
            byte[] keyBytes = key.getBytes(ENCODING_TYPE);
            secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ivParameterSpec = new IvParameterSpec(keyBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            StackTraceElement[] ste = e.getStackTrace();
            log.error(String.valueOf(ste[ste.length - 1]));
        }

        return new Encryptor();
    }



    private static void validation(String key) {
        Optional.ofNullable(key)
                .filter(x -> !x.isEmpty())
                .filter(x -> x.length() == 16)
                .orElseThrow(IllegalArgumentException::new);
    }




    public static class Encryptor {

        public String encrypt(String str) {
            if (str == null || str.isEmpty()) return null;

            try {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
                byte[] encrypted = cipher.doFinal(str.getBytes(ENCODING_TYPE));
                return new String(Base64.getEncoder().encode(encrypted), ENCODING_TYPE);
            } catch (Exception e) {
                StackTraceElement[] ste = e.getStackTrace();
                log.error(String.valueOf(ste[ste.length - 1]));
                return str;
            }
        }

        public String decrypt(String str) {
            if (str == null || str.isEmpty()) return null;

            try {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
                byte[] decoded = Base64.getDecoder().decode(str.getBytes(ENCODING_TYPE));
                return new String(cipher.doFinal(decoded), ENCODING_TYPE);
            } catch (Exception e) {
                StackTraceElement[] ste = e.getStackTrace();
                log.error(String.valueOf(ste[ste.length - 1]));
                return str;
            }
        }
    }

}
