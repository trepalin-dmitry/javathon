package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.dm.entity.user.Access;
import ru.filit.mdma.dm.repository.AccessRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:config/app.properties")
public class AccessRepositoryImpl implements AccessRepository {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapperYaml;
    private Access[] accessesYaml;
    @Value("${access}")
    private String location;

    @Autowired
    public AccessRepositoryImpl(ResourceLoader resourceLoader,
                                @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resourceLoader = resourceLoader;
        this.objectMapperYaml = objectMapperYaml;
    }

    @Override
    public List<Access> getAccess(int version, String role) {
        StringBuilder sb = new StringBuilder(location);
        sb.insert(sb.lastIndexOf("."), version);
        loadAccessData(String.valueOf(sb));
        return Arrays.stream(accessesYaml)
                .filter(access -> access.getRole().equals(role))
                .collect(Collectors.toList());
    }

    private void loadAccessData(String location) {
        try {
            accessesYaml = objectMapperYaml.readValue(resourceLoader.getResource(location).getFile(), Access[].class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
