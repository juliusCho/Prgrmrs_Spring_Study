package com.github.prgrms.socialserver.global.utils;

import com.github.prgrms.socialserver.global.Secrets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UtilTest {

    private static final Logger log = LoggerFactory.getLogger(UtilTest.class);

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
        String enc = EncryptUtil.setEncryption(Secrets.PASSWD_KEY).encrypt("Julius Password");
        log.debug("encrypted: {}", enc);

        assert(EncryptUtil.setEncryption(Secrets.PASSWD_KEY).decrypt(enc).equals("Julius Password"));
    }

    @Test
    public void dateUtil_currentSystemTime_convertToUTCAndBackToLocal() throws Exception {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        Date utc = DateUtil.convertToUTCDate(simpleDateFormat.format(dt));
        log.debug("UTC: {}", simpleDateFormat.format(utc));
        log.debug("Local: {}", DateUtil.convertToLocalString(utc));

        assert(DateUtil.convertToLocalString(utc).equals(simpleDateFormat.format(dt)));
    }

}
