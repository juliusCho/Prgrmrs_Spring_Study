package com.github.prgrms.socialserver.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

public final class DateUtil {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertToLocalString(LocalDateTime date) {
        if (date == null) return now().format(format);
        LocalDateTime local = date.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return local.format(format);
    }

    public static LocalDateTime convertToUTCDate(String date) {
        if (date == null || date.isEmpty()) return now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime local = LocalDateTime.parse(date, format);
        return local.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

}
