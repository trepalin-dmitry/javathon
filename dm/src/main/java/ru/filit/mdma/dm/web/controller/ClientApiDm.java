package ru.filit.mdma.dm.web.controller;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.dm.database.Balance;
import ru.filit.mdma.dm.database.DatabaseService;
import ru.filit.mdma.dm.web.dto.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/dm")
public class ClientApiDm implements ClientApi {
    private final DatabaseService databaseService;

    public ClientApiDm(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAccount(ClientIdDto clientIdDto) {
        var result = databaseService
                .getAccounts()
                .stream()
                .filter(contact -> contact.getClientId().equals(clientIdDto.getId()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CurrentBalanceDto> getAccountBalance(AccountNumberDto accountNumberDto) {
        var balanceAmount = databaseService
                .getBalances()
                .stream()
                .filter(f -> f.getAccountNumber().equals(accountNumberDto.getAccountNumber()))
                .max(Comparator.comparingLong(Balance::getBalanceDate))
                .orElseThrow()
                .getAmount();

        return new ResponseEntity<>(new CurrentBalanceDto().balanceAmount(balanceAmount), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OperationDto>> getAccountOperations(OperationSearchDto operationSearchDto) {
        var result =  databaseService
                .getOperations()
                .stream()
                .filter(f -> f.getAccountNumber().equals(operationSearchDto.getAccountNumber()))
                .sorted((o1, o2) -> Long.compare(Long.parseLong(o2.getOperDate()), Long.parseLong(o1.getOperDate())))
                .limit(Long.parseLong(operationSearchDto.getQuantity()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<List<ClientDto>> getClient(ClientSearchDto clientSearchDto) {
        var result = databaseService
                .getClients()
                .stream()
                .filter(client ->
                        (clientSearchDto.getId() == null || clientSearchDto.getId().equals(client.getId()))
                                && (clientSearchDto.getLastname() == null || clientSearchDto.getLastname().equals(client.getLastname()))
                                && (clientSearchDto.getFirstname() == null || clientSearchDto.getFirstname().equals(client.getFirstname()))
                                && (clientSearchDto.getPatronymic() == null || clientSearchDto.getPatronymic().equals(client.getPatronymic()))
                                && (clientSearchDto.getBirthDate() == null || clientSearchDto.getBirthDate().equals(client.getBirthDate()))
                                && (clientSearchDto.getPassport() == null || clientSearchDto.getPassport().equals(client.getPassportSeries() + client.getPassportNumber()))
                                && (clientSearchDto.getInn() == null || clientSearchDto.getInn().equals(client.getInn()))
                )
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientLevelDto> getClientLevel(ClientIdDto clientIdDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<List<ContactDto>> getContact(ClientIdDto clientIdDto) {
        var result = databaseService
                .getContacts()
                .stream()
                .filter(contact -> contact.getClientId().equals(clientIdDto.getId()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
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
