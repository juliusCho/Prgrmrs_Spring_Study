package com.github.prgrms.socialserver.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertToLocalString(LocalDateTime date) {
        if (date == null) return null;
        LocalDateTime local = date.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return local.format(format);
    }

    public static LocalDateTime convertToUTCDate(String date) {
        if (date == null || date.isEmpty()) return null;
        LocalDateTime local = LocalDateTime.parse(date, format);
        return local.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

}
