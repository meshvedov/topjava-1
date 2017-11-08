package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Dates {
    private Dates() {}

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime == null ? LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)) : localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
