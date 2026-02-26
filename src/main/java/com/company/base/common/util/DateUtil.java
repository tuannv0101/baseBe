package com.company.base.common.util;

import com.company.base.common.constant.AppConstants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.DATE_TIME_FORMAT);

    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(formatter);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
