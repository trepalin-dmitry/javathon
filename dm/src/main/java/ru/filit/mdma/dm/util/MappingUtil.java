package ru.filit.mdma.dm.util;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.dm.entity.client.Contact;
import ru.filit.mdma.dm.entity.client.ContactType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class MappingUtil {

    @Named("convertToLocalDate")
    public static LocalDate convertToLocalDate(Long date) {
        return LocalDate.ofInstant(Instant.ofEpochMilli(date), ZoneId.of("UTC"));
    }

    @Named("getShortcutAccountNumber")
    public static String getShortcutAccountNumber(String number) {
        return StringUtils.right(number, 4);
    }

    @Named("getPassportSeries")
    public static String getPassportSeries(String passport) {
        return !StringUtils.isEmpty(passport) ? passport.substring(0, passport.indexOf(' ')) : null;
    }

    @Named("getPassportNumber")
    public static String getPassportNumber(String passport) {
        return !StringUtils.isEmpty(passport) ? passport.substring(passport.lastIndexOf(' ')).trim() : null;
    }

    @Named("convertAmount")
    public static String convertAmount(BigDecimal amount) {
        return String.valueOf(amount.setScale(2, RoundingMode.HALF_DOWN));
    }

    @Named("convertToStringDateTime")
    public static String convertToStringDateTime(Long date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date),
                ZoneId.of("UTC"));
        return String.valueOf(dateTime);
    }

    @Named("convertToStringDate")
    public static Long convertToStringDate(String date) {
        if (Objects.isNull(date)) {
            return null;
        }
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ZoneId zoneId = ZoneId.of("UTC");
        return localDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }

    @Named("getShortcutContactValue")
    public static String getShortcutContactValue(Contact contact) {
        if (contact.getType().equals(ContactType.PHONE)) {
            return StringUtils.right(contact.getValue(), 4);
        } else {
            String value = contact.getValue();
            return value.substring(value.lastIndexOf('@') - 1);
        }
    }
}
