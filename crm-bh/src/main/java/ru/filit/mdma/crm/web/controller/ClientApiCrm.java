package ru.filit.mdma.crm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.crm.web.dto.*;
import ru.filit.mdma.dm.web.controller.DefaultApi;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ClientApiCrm implements ClientApi {
    private final DefaultApi defaultApi;

    public ClientApiCrm(DefaultApi defaultApi) {
        this.defaultApi = defaultApi;
    }

    @Override
    public ResponseEntity<List<ClientDto>> findClient(ClientSearchDto clientSearchDto) {
        var result = defaultApi.getClient(new ru.filit.mdma.dm.web.dto.ClientSearchDto()
                        .id(clientSearchDto.getId())
                        .lastname(clientSearchDto.getLastname())
                        .firstname(clientSearchDto.getFirstname())
                        .patronymic(clientSearchDto.getPatronymic())
                        .birthDate(clientSearchDto.getBirthDate())
                        .passport(clientSearchDto.getPassport())
                        .inn(clientSearchDto.getInn()))
                .stream()
                .map(m -> new ClientDto()
                        .id(m.getId())
                        .lastname(m.getLastname())
                        .firstname(m.getFirstname())
                        .patronymic(m.getPatronymic())
                        .birthDate(m.getBirthDate())
                        .passportSeries(m.getPassportSeries())
                        .passportNumber(m.getPassportNumber())
                        .inn(m.getInn())
                        .address(m.getAddress()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientDto> getClient(ClientIdDto clientIdDto) {
        var clientDto = defaultApi.getClient(new ru.filit.mdma.dm.web.dto.ClientSearchDto()
                .id(clientIdDto.getId())).get(0);

        var result = new ClientDto()
                .id(clientDto.getId())
                .lastname(clientDto.getLastname())
                .firstname(clientDto.getFirstname())
                .patronymic(clientDto.getPatronymic())
                .birthDate(clientDto.getBirthDate())
                .passportSeries(clientDto.getPassportSeries())
                .passportNumber(clientDto.getPassportNumber())
                .inn(clientDto.getInn())
                .address(clientDto.getAddress());

        var clientIdDto2 = new ru.filit.mdma.dm.web.dto.ClientIdDto()
                .id(clientIdDto.getId());

        for (ru.filit.mdma.dm.web.dto.ContactDto contactDto : defaultApi.getContact(clientIdDto2)) {
            result.addContactsItem(new ContactDto()
                    .id(contactDto.getId())
                    .clientId(contactDto.getClientId())
                    .type(contactDto.getType())
                    .value(contactDto.getValue())
                    .shortcut(contactDto.getShortcut())
            );
        }

        for (var accountDto : defaultApi.getAccount(clientIdDto2)) {
            result.addAccountsItem(new AccountDto()
                    .number(accountDto.getNumber())
                    .clientId(accountDto.getClientId())
                    .type(accountDto.getType())
                    .currency(accountDto.getCurrency())
                    .status(accountDto.getStatus())
                    .openDate(accountDto.getOpenDate())
                    .closeDate(accountDto.getCloseDate())
                    .deferment(accountDto.getDeferment())
                    .shortcut(accountDto.getShortcut())
                    .balanceAmount(accountDto.getClientId())
            );
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClientLevelDto> getClientLevel(ClientIdDto clientIdDto) {
        throw new RuntimeException();
    }

    @Override
    public ResponseEntity<List<OperationDto>> getLastOperations(AccountNumberDto accountNumberDto) {
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

