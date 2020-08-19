package com.github.prgrms.socialserver.global.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UtilTest {

    private static final Logger log = LoggerFactory.getLogger(UtilTest.class);

    private static final String PASSWD_KEY = "password12345678";

    @Test
    public void encryptUtil_constructWithInvalidKeyLength_IllegalArgumentException() throws Exception {
        try {
            fail(EncryptUtil.setEncryption("password1234567").toString());
        } catch (Exception e) {
            assertTrue(e.getClass().equals(IllegalArgumentException.class));
        }
    }

    @Test
    public void encryptUtil_withRandomPasswd_EncryptDecryptAsExpected() throws Exception {
        String enc = EncryptUtil.setEncryption(PASSWD_KEY).encrypt("");
        log.debug("encrypted: {}", enc);

//        assert(EncryptUtil.setEncryption(Secrets.PASSWD_KEY).decrypt("prQDqRzJIFKyPEtZ5yEMYw==").equals("new@email.com"));
    }

    @Test
    public void dateUtil_currentSystemTime_convertToUTCAndBackToLocal() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime utc = DateUtil.convertToUTCDate(LocalDateTime.now().format(format));
        String local = DateUtil.convertToLocalString(utc);
        log.debug("UTC: {}", utc.format(format));
        log.debug("Local: {}", local);

        assert(DateUtil.convertToUTCDate(local).equals(utc));
    }

}
