package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.Temporal;

public class DateConverter implements ConverterFactory<String, LocalDate> {
    @Override
    public <T extends LocalDate> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToLocalDateConverter(targetType);
    }

    private class StringToLocalDateConverter<T extends LocalDate> implements Converter<String, T> {

        private Class<T> dateTime;
        StringToLocalDateConverter(Class<T> tClass) {
            dateTime = tClass;
        }

        @Nullable
        @Override
        public T convert(String source) {
            return StringUtils.isEmpty(source) ? null : (T) java.time.LocalDate.parse(source);
        }
    }
}
