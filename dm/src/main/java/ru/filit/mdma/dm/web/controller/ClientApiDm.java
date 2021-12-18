package ru.filit.mdma.dm.web.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;
import ru.filit.mdma.dm.DatabaseService;
import ru.filit.mdma.dm.web.dto.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dm")
public class ClientApiDm implements ClientApi {
    private final DatabaseService databaseService;

    public ClientApiDm(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAccount(ClientIdDto clientIdDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<CurrentBalanceDto> getAccountBalance(AccountNumberDto accountNumberDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<List<OperationDto>> getAccountOperations(OperationSearchDto operationSearchDto) {
        throw new RuntimeException();
    }

    @SneakyThrows
    @Override
    public ResponseEntity<List<ClientDto>> getClient(ClientSearchDto clientSearchDto) {
        Yaml yaml = new Yaml();
        var resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:datafiles/clients.yml");
        var reader = new InputStreamReader(resource.getInputStream());

        ArrayList<Map<String, String>> objects = yaml.load(reader);

        var result = new ArrayList<ClientDto>();

        for (ClientDto client : databaseService.getClients()) {
            if (
                    (clientSearchDto.getId() == null || clientSearchDto.getId().equals(client.getId()))
                            && (clientSearchDto.getLastname() == null || clientSearchDto.getLastname().equals(client.getLastname()))
                            && (clientSearchDto.getFirstname() == null || clientSearchDto.getFirstname().equals(client.getFirstname()))
                            && (clientSearchDto.getPatronymic() == null || clientSearchDto.getPatronymic().equals(client.getPatronymic()))
                            && (clientSearchDto.getBirthDate() == null || clientSearchDto.getBirthDate().equals(client.getBirthDate()))
                            && (clientSearchDto.getPassport() == null || clientSearchDto.getPassport().equals(client.getPassportSeries() + client.getPassportNumber()))
                            && (clientSearchDto.getInn() == null || clientSearchDto.getInn().equals(client.getInn()))
            ) {
                result.add(client);
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientLevelDto> getClientLevel(ClientIdDto clientIdDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<List<ContactDto>> getContact(ClientIdDto clientIdDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<LoanPaymentDto> getLoanPayment(AccountNumberDto accountNumberDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<ContactDto> saveContact(ContactDto contactDto) {
        throw new RuntimeException();
    }
}
