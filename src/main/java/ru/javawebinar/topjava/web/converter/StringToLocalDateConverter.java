package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Nullable
    @Override
    public LocalDate convert(String source) {
        return StringUtils.isEmpty(source) ? null : LocalDate.parse(source);
    }
}