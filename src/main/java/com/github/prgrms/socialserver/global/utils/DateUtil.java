package com.github.prgrms.socialserver.global.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public final class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);

    private static final ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.ofEpochMilli(1484063246L * 1000L));




    public static String convertToLocalString(Date date) {
        if (date == null) return null;
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.ofInstant(date.toInstant(), offset), ZoneOffset.UTC);
        return format.format(new Date(odt.toInstant().toEpochMilli()));
    }

    public static Date convertToUTCDate(String date) {
        if (date == null || date.isEmpty()) return null;
        try {
            Date dt = format.parse(date);
            OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.ofInstant(dt.toInstant(), ZoneOffset.UTC), offset);
            return new Date(odt.toInstant().toEpochMilli());
        } catch (ParseException e) {
            StackTraceElement[] ste = e.getStackTrace();
            log.error(String.valueOf(ste[ste.length - 1]));
            return null;
        }
    }

}
