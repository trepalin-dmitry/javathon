package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.dm.entity.client.Contact;
import ru.filit.mdma.dm.repository.ContactRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:config/app.properties")
public class ContactRepositoryImpl implements ContactRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private Contact[] contactsYaml;

    @Autowired
    public ContactRepositoryImpl(@Value("${contacts}") String location, ResourceLoader resourceLoader,
                                 @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadContactData() {
        try {
            contactsYaml = objectMapperYaml.readValue(resource.getFile(), Contact[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contact save(Contact contact) {
        Map<String, Contact> contacts = Arrays.stream(contactsYaml)
                .collect(Collectors.toMap(Contact::getId, Function.identity()));
        if (Objects.isNull(contact.getId())) {
            int id = contacts.keySet().stream()
                    .max(String::compareTo)
                    .map(Integer::valueOf)
                    .orElse((int) (Math.random() * 1000000));
            contact.setId(String.valueOf(++id));
        } else if (!contacts.containsKey(contact.getId())) {
            return null;
        }
        contacts.put(contact.getId(), contact);
        contactsYaml = contacts.values().toArray(new Contact[0]);
        saveContactData();
        return contact;
    }

    @Override
    public List<Contact> getContact(String clientId) {
        return Arrays.stream(contactsYaml)
                .filter(contact -> contact.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    private void saveContactData() {
        try {
            objectMapperYaml.writeValue(resource.getFile(), contactsYaml);
            loadContactData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
