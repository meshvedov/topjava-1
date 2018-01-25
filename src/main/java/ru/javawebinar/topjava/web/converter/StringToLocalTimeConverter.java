package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Nullable
    @Override
    public LocalTime convert(String source) {
        return StringUtils.isEmpty(source) ? null : LocalTime.parse(source);
    }
}
