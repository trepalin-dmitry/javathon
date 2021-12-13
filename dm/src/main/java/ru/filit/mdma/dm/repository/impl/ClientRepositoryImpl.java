package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.dm.entity.client.Client;
import ru.filit.mdma.dm.repository.ClientRepository;
import ru.filit.mdma.dm.web.exception.ClientNotFoundException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Repository
@PropertySource("classpath:config/app.properties")
public class ClientRepositoryImpl implements ClientRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private Client[] clientsYaml;

    @Autowired
    public ClientRepositoryImpl(@Value("${clients}") String location, ResourceLoader resourceLoader,
                                @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadClientData() {
        try {
            clientsYaml = objectMapperYaml.readValue(resource.getFile(), Client[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getClient(String clientId) {
        return Arrays.stream(clientsYaml)
                .filter(client -> client.getId().equals(clientId))
                .findFirst()
                .orElseThrow(
                        () -> new ClientNotFoundException("client with id " + clientId + " not found")
                );
    }

    @Override
    public List<Client> getClients(Client client) {
        Map<String, Client> clients = new HashMap<>();
        Map<String, Object> fields = getNonNullFields(client);
        for (Client entity : clientsYaml) {
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                try {
                    Field entityField = entity.getClass().getDeclaredField(entry.getKey());
                    entityField.setAccessible(true);
                    if (entityField.get(entity).equals(entry.getValue())) {
                        clients.putIfAbsent(entity.getId(), entity);
                        break;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>(clients.values());
    }

    private Map<String, Object> getNonNullFields(Client client) {
        Map<String, Object> fields = new HashMap<>();
        for (Field field : client.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (Objects.nonNull(field.get(client))) {
                    fields.put(field.getName(), field.get(client));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }
}
