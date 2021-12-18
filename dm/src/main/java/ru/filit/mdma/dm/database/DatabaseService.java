package ru.filit.mdma.dm.database;

import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.utils.MaskUtils;
import ru.filit.mdma.dm.utils.YamlUtils;
import ru.filit.mdma.dm.web.dto.AccountDto;
import ru.filit.mdma.dm.web.dto.ClientDto;
import ru.filit.mdma.dm.web.dto.ContactDto;
import ru.filit.mdma.dm.web.dto.OperationDto;

import java.util.Collection;
import java.util.Map;

@Service
public class DatabaseService {
    public Collection<ClientDto> getClients() {
        return YamlUtils.parse("datafiles/clients.yml", this::parseClient);
    }

    public Collection<ContactDto> getContacts() {
        return YamlUtils.parse("datafiles/contacts.yml", this::parseContact);
    }

    public Collection<AccountDto> getAccounts() {
        return YamlUtils.parse("datafiles/accounts.yml", this::parseAccount);
    }

    public Collection<Balance> getBalances() {
        return YamlUtils.parse("datafiles/balances.yml", this::parseBalance);
    }

    public Collection<OperationDto> getOperations() {
        return YamlUtils.parse("datafiles/operations.yml", this::parseOperation);
    }

    private ClientDto parseClient(Map<String, Object> map) {
        return new ClientDto()
                .id(String.valueOf(map.get("id")))
                .lastname(String.valueOf(map.get("lastname")))
                .firstname(String.valueOf(map.get("firstname")))
                .patronymic(String.valueOf(map.get("patronymic")))
                .birthDate(String.valueOf(map.get("birthDate")))
                .passportSeries(String.valueOf(map.get("passportSeries")))
                .passportNumber(String.valueOf(map.get("passportNumber")))
                .inn(String.valueOf(map.get("inn")))
                .address(String.valueOf(map.get("address")));
    }

    private ContactDto parseContact(Map<String, Object> map) {
        var result = new ContactDto()
                .id(String.valueOf(map.get("id")))
                .clientId(String.valueOf(map.get("clientId")))
                .type(String.valueOf(map.get("type")))
                .value(String.valueOf(map.get("value")));

        String shortcut = null;
        if (result.getType().equals("PHONE")){
            shortcut = MaskUtils.last(result.getValue(), 4);
        } else if (result.getType().equals("EMAIL")){
            shortcut = result.getValue().substring(result.getValue().indexOf("@") - 1);
        }
        result.shortcut(shortcut);

        return result;
    }

    private AccountDto parseAccount(Map<String, Object> map) {
        var result = new AccountDto()
                .number(String.valueOf(map.get("number")))
                .clientId(String.valueOf(map.get("clientId")))
                .type(String.valueOf(map.get("type")))
                .currency(String.valueOf(map.get("currency")))
                .status(String.valueOf(map.get("status")))
                .openDate(String.valueOf(map.get("openDate")))
                .closeDate(String.valueOf(map.get("closeDate")))
                .deferment(String.valueOf(map.get("deferment")));

        result.shortcut(MaskUtils.last(result.getNumber(), 4));
        return result;
    }

    private Balance parseBalance(Map<String, Object> map) {
        return new Balance()
                .setAccountNumber(String.valueOf(map.get("accountNumber")))
                .setBalanceDate(Long.parseLong(String.valueOf(map.get("balanceDate"))))
                .setAmount(String.valueOf(map.get("amount")));
    }

    private OperationDto parseOperation(Map<String, Object> map) {
        return new OperationDto()
                .type(String.valueOf(map.get("type")))
                .accountNumber(String.valueOf(map.get("accountNumber")))
                .operDate(String.valueOf(map.get("operDate")))
                .amount(String.valueOf(map.get("amount")))
                .description(String.valueOf(map.get("description")));
    }
}

