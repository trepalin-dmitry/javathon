package ru.filit.mdma.dm.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.dm.entity.client.ContactType;
import ru.filit.mdma.dm.web.dto.ClientSearchDto;
import ru.filit.mdma.dm.web.dto.ContactDto;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Component
@PropertySource("classpath:config/app.properties")
public class ValidationUtil {

    @Value("#{'${phone.number.patterns}'.split(';')}")
    private List<String> phoneNumberPatterns;
    @Value("#{'${email.patterns}'.split(';')}")
    private List<String> emailPatterns;
    @Value("#{'${passport.patterns}'.split(';')}")
    private List<String> passportPatterns;

    public <T> List<T> checkResponse(List<T> list) {
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return list;
    }

    public <T> T checkResponse(T object) {
        if (Objects.isNull(object)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return object;
    }

    public <T> void checkAllFieldsIsNull(T object) {
        boolean allFieldsIsNull = true;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (Objects.nonNull(field.get(object))) {
                    allFieldsIsNull = false;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (allFieldsIsNull) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void checkPassport(ClientSearchDto clientSearchDto) {
        if (Objects.nonNull(clientSearchDto.getPassport())) {
            checkValue(passportPatterns, clientSearchDto.getPassport());
        }
    }

    public void checkContact(ContactDto contactDto) {
        if (contactDto.getType().equals(ContactType.PHONE.getValue())) {
            checkValue(phoneNumberPatterns, contactDto.getValue());
        } else {
            checkValue(emailPatterns, contactDto.getValue());
        }
    }

    private void checkValue(List<String> patterns, String value) {
        if (patterns.stream().noneMatch(value::matches)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
