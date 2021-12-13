package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.dm.entity.account.Account;
import ru.filit.mdma.dm.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:config/app.properties")
public class AccountRepositoryImpl implements AccountRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private Account[] accountsYaml;

    @Autowired
    public AccountRepositoryImpl(@Value("${accounts}") String location, ResourceLoader resourceLoader,
                                 @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadAccountData() {
        try {
            accountsYaml = objectMapperYaml.readValue(resource.getFile(), Account[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return Arrays.stream(accountsYaml)
                .filter(account -> account.getNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Account> getAccount(String clientId) {
        return Arrays.stream(accountsYaml)
                .filter(account -> account.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }
}
