package za.ac.up.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {

    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_TIME;
    @Override
    public String convertToDatabaseColumn(LocalTime locDate) {
        return (locDate == null ? null : dtf.format(locDate));
    }

    @Override
    public LocalTime convertToEntityAttribute(String sqlDate) {
        return (sqlDate == null ? null : LocalTime.parse(sqlDate));
    }
}