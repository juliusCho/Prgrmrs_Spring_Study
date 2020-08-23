package com.github.prgrms.socialserver.global.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UtilTest {

    private static final Logger log = LoggerFactory.getLogger(UtilTest.class);

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
