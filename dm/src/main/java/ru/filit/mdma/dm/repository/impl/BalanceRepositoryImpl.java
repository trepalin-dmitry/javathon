package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.dm.entity.account.AccountBalance;
import ru.filit.mdma.dm.repository.BalanceRepository;
import ru.filit.mdma.dm.util.MappingUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;

@Repository
@PropertySource("classpath:config/app.properties")
public class BalanceRepositoryImpl implements BalanceRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private AccountBalance[] accountBalancesYaml;

    @Autowired
    public BalanceRepositoryImpl(@Value("${balances}") String location, ResourceLoader resourceLoader,
                                 @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadBalanceData() {
        try {
            accountBalancesYaml = objectMapperYaml.readValue(resource.getFile(), AccountBalance[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccountBalance getAccountBalance(String accountNumber, LocalDate localDate) {
        AccountBalance accountBalance = null;
        for (AccountBalance balance : accountBalancesYaml) {
            if (!balance.getAccountNumber().equals(accountNumber)) {
                continue;
            }
            LocalDate balanceDate = MappingUtil.convertToLocalDate(balance.getBalanceDate());
            if (balanceDate.getYear() == localDate.getYear()
                    && balanceDate.getMonthValue() == localDate.getMonthValue()) {
                accountBalance = balance;
                break;
            }
        }
        return accountBalance;
    }
}
