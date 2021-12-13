package ru.filit.mdma.crm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.crm.entity.user.User;
import ru.filit.mdma.crm.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Repository
@PropertySource("classpath:config/app.properties")
public class UserRepositoryImpl implements UserRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private User[] usersYaml;

    @Autowired
    public UserRepositoryImpl(@Value("${users}") String location, ResourceLoader resourceLoader,
                              @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadUserData() {
        try {
            usersYaml = objectMapperYaml.readValue(resource.getFile(), User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Arrays.stream(usersYaml)
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
