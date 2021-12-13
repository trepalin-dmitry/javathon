package ru.filit.mdma.dms.util;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.dms.web.dto.AccessDto;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:config/app.properties")
public class AccessUtil {

    @Value("${user.roles}")
    private List<String> userRoles;
    private final IgniteCache<String, String> cache;

    @Autowired
    public AccessUtil(Ignite ignite) {
        this.cache = ignite.getOrCreateCache("dms");
    }

    public void checkRole(String role) {
        if (userRoles.stream().noneMatch(role::matches)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public <T> List<String> getMaskedFieldsNames(List<AccessDto> accesses, String entity, Class<T> clazz) {
        List<String> openFields = accesses
                .stream()
                .filter(accessDto -> accessDto.getEntity().equals(entity))
                .map(AccessDto::getProperty)
                .collect(Collectors.toList());
        List<String> maskedFields = Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
        maskedFields.removeAll(openFields);
        return maskedFields;
    }

    public <T> void maskedValue(List<String> maskedFields, T[] objects) {
        Arrays.stream(objects).forEach(object -> maskedValue(maskedFields, object));
    }

    public <T> void maskedValue(List<String> maskedFields, T object) {
        for (String maskedField : maskedFields) {
            try {
                Field field = object.getClass().getDeclaredField(maskedField);
                field.setAccessible(true);
                String value = String.valueOf(field.get(object));
                String token = createToken(value);
                field.set(object, token);
                cache.put(token, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> void unMaskedValue(List<String> maskedFields, T object) {
        for (String maskedField : maskedFields) {
            try {
                Field field = object.getClass().getDeclaredField(maskedField);
                field.setAccessible(true);
                String token = String.valueOf(field.get(object));
                if (cache.containsKey(token)) {
                    field.set(object, cache.get(token));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> String createToken(T object) {
        UUID uuid = UUID.nameUUIDFromBytes(object.toString().getBytes());
        String token = uuid.toString().toUpperCase();
        token = token.replaceAll("-", "");
        return "#" + token + "#";
    }
}
