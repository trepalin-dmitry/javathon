package ru.filit.mdma.dm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.dm.service.*;
import ru.filit.mdma.dm.util.ValidationUtil;
import ru.filit.mdma.dm.web.dto.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClientApi {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final ClientService clientService;
    private final ContactService contactService;
    private final OperationService operationService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ClientApi(AccountService accountService, BalanceService balanceService, ClientService clientService,
                     ContactService contactService, OperationService operationService, ValidationUtil validationUtil) {
        this.accountService = accountService;
        this.balanceService = balanceService;
        this.clientService = clientService;
        this.contactService = contactService;
        this.operationService = operationService;
        this.validationUtil = validationUtil;
    }

    @PostMapping
    public ResponseEntity<List<ClientDto>> getClient(@RequestBody ClientSearchDto clientSearchDto) {
        validationUtil.checkAllFieldsIsNull(clientSearchDto);
        validationUtil.checkPassport(clientSearchDto);
        List<ClientDto> clients = clientService.getClient(clientSearchDto);
        return ResponseEntity.ok(validationUtil.checkResponse(clients));
    }

    @PostMapping("/contact")
    public ResponseEntity<List<ContactDto>> getContact(@Valid @RequestBody ClientIdDto clientIdDto) {
        List<ContactDto> contacts = contactService.getContact(clientIdDto);
        return ResponseEntity.ok(validationUtil.checkResponse(contacts));
    }

    @PostMapping("/account")
    public ResponseEntity<List<AccountDto>> getAccount(@Valid @RequestBody ClientIdDto clientIdDto) {
        List<AccountDto> accounts = accountService.getAccount(clientIdDto);
        return ResponseEntity.ok(validationUtil.checkResponse(accounts));
    }

    @PostMapping("/account/balance")
    public ResponseEntity<CurrentBalanceDto> getAccountBalance(@Valid @RequestBody AccountNumberDto accountNumberDto) {
        CurrentBalanceDto currentBalance = balanceService.getAccountBalance(
                accountNumberDto.getAccountNumber(), LocalDate.now());
        return ResponseEntity.ok(validationUtil.checkResponse(currentBalance));
    }

    @PostMapping("/account/operation")
    public ResponseEntity<List<OperationDto>> getAccountOperations(
            @Valid @RequestBody OperationSearchDto operationSearchDto) {
        List<OperationDto> operations = operationService.getAccountOperations(operationSearchDto);
        return ResponseEntity.ok(validationUtil.checkResponse(operations));
    }

    @PostMapping("/contact/save")
    public ResponseEntity<ContactDto> saveContact(@Valid @RequestBody ContactDto contactDto) {
        validationUtil.checkContact(contactDto);
        ContactDto contact = contactService.saveContact(contactDto);
        return ResponseEntity.ok(validationUtil.checkResponse(contact));
    }

    @PostMapping("/level")
    public ResponseEntity<ClientLevelDto> getClientLevel(@Valid @RequestBody ClientIdDto clientIdDto) {
        ClientLevelDto clientLevel = clientService.getClientLevel(clientIdDto);
        return ResponseEntity.ok(validationUtil.checkResponse(clientLevel));
    }

    @PostMapping("/account/loan-payment")
    public ResponseEntity<LoanPaymentDto> getLoanPayment(@Valid @RequestBody AccountNumberDto accountNumberDto) {
        LoanPaymentDto loanPayment = accountService.getLoanPayment(accountNumberDto);
        return ResponseEntity.ok(validationUtil.checkResponse(loanPayment));
    }
}
