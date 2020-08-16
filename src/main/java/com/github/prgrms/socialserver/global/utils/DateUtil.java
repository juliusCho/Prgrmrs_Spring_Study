package com.github.prgrms.socialserver.global.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public final class DateUtil {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);

    private static final ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(Instant.ofEpochMilli(1484063246L * 1000L));




    public static String convertToLocalString(Date date) {
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.ofInstant(date.toInstant(), offset), ZoneOffset.UTC);
        return format.format(new Date(odt.toInstant().toEpochMilli()));
    }

    public static Date convertToUTCDate(String date) throws ParseException {
        Date dt = format.parse(date);
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.ofInstant(dt.toInstant(), ZoneOffset.UTC), offset);
        return new Date(odt.toInstant().toEpochMilli());
    }

}