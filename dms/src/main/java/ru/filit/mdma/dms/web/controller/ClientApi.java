package ru.filit.mdma.dms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.filit.mdma.dms.util.AccessUtil;
import ru.filit.mdma.dms.web.dto.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@PropertySource("classpath:config/app.properties")
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClientApi {

    @Value("${dm.url}")
    private String dmUrl;
    @Value("${access.url}")
    private String accessUrl;
    @Value("${access.version}")
    private String accessVersion;
    private final RestTemplate restTemplate;
    private final AccessUtil accessUtil;

    @Autowired
    public ClientApi(RestTemplate restTemplate, AccessUtil accessUtil) {
        this.restTemplate = restTemplate;
        this.accessUtil = accessUtil;
    }

    @PostMapping
    public ResponseEntity<List<ClientDto>> getClient(@RequestHeader("CRM-User-Role") String role,
                                                     @RequestBody ClientSearchDto clientSearchDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "client",
                ClientSearchDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, clientSearchDto);
        ResponseEntity<ClientDto[]> responseEntity = restTemplate
                .postForEntity(dmUrl, clientSearchDto, ClientDto[].class);
        ClientDto[] clients = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "client",
                ClientDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, clients);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(clients)));
    }

    @PostMapping("/contact")
    public ResponseEntity<List<ContactDto>> getContact(@RequestHeader("CRM-User-Role") String role,
                                                       @Valid @RequestBody ClientIdDto clientIdDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "client",
                ClientIdDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, clientIdDto);
        ResponseEntity<ContactDto[]> responseEntity = restTemplate
                .postForEntity(dmUrl + "/contact", clientIdDto, ContactDto[].class);
        ContactDto[] contacts = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "contact",
                ContactDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, contacts);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(contacts)));
    }

    @PostMapping("/account")
    public ResponseEntity<List<AccountDto>> getAccount(@RequestHeader("CRM-User-Role") String role,
                                                       @Valid @RequestBody ClientIdDto clientIdDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "client",
                ClientIdDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, clientIdDto);
        ResponseEntity<AccountDto[]> responseEntity = restTemplate
                .postForEntity(dmUrl + "/account", clientIdDto, AccountDto[].class);
        AccountDto[] accounts = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "account",
                AccountDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, accounts);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(accounts)));
    }

    @PostMapping("/account/balance")
    public ResponseEntity<CurrentBalanceDto> getAccountBalance(@RequestHeader("CRM-User-Role") String role,
                                                               @Valid @RequestBody AccountNumberDto accountNumberDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "account",
                AccountNumberDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, accountNumberDto);
        ResponseEntity<CurrentBalanceDto> responseEntity = restTemplate
                .postForEntity(dmUrl + "/account/balance", accountNumberDto, CurrentBalanceDto.class);
        CurrentBalanceDto currentBalance = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "currentBalance",
                CurrentBalanceDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, currentBalance);
        return ResponseEntity.ok(currentBalance);
    }

    @PostMapping("/account/operation")
    public ResponseEntity<List<OperationDto>> getAccountOperations(@RequestHeader("CRM-User-Role") String role,
                                                                   @Valid @RequestBody OperationSearchDto operationSearchDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "operation",
                OperationSearchDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, operationSearchDto);
        ResponseEntity<OperationDto[]> responseEntity = restTemplate
                .postForEntity(dmUrl + "/account/operation", operationSearchDto, OperationDto[].class);
        OperationDto[] operations = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "operation",
                OperationDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, operations);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(operations)));
    }

    @PostMapping("/contact/save")
    public ResponseEntity<ContactDto> saveContact(@RequestHeader("CRM-User-Role") String role,
                                                  @Valid @RequestBody ContactDto contactDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "contact",
                ContactDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, contactDto);
        ResponseEntity<ContactDto> responseEntity = restTemplate
                .postForEntity(dmUrl + "/contact/save", contactDto, ContactDto.class);
        ContactDto contact = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "contact",
                ContactDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, contact);
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/level")
    public ResponseEntity<ClientLevelDto> getClientLevel(@RequestHeader("CRM-User-Role") String role,
                                                         @Valid @RequestBody ClientIdDto clientIdDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "client",
                ClientIdDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, clientIdDto);
        ResponseEntity<ClientLevelDto> responseEntity = restTemplate
                .postForEntity(dmUrl + "/level", clientIdDto, ClientLevelDto.class);
        ClientLevelDto clientLevel = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "clientLevel",
                ClientLevelDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, clientLevel);
        return ResponseEntity.ok(clientLevel);
    }

    @PostMapping("/account/loan-payment")
    public ResponseEntity<LoanPaymentDto> getLoanPayment(@RequestHeader("CRM-User-Role") String role,
                                                         @Valid @RequestBody AccountNumberDto accountNumberDto) {
        accessUtil.checkRole(role);
        List<AccessDto> accesses = getAccess(role);
        List<String> maskedFieldsRequest = accessUtil.getMaskedFieldsNames(accesses, "account",
                AccountNumberDto.class);
        accessUtil.unMaskedValue(maskedFieldsRequest, accountNumberDto);
        ResponseEntity<LoanPaymentDto> responseEntity = restTemplate
                .postForEntity(dmUrl + "/account/loan-payment", accountNumberDto, LoanPaymentDto.class);
        LoanPaymentDto loanPayment = responseEntity.getBody();
        List<String> maskedFieldsResponse = accessUtil.getMaskedFieldsNames(accesses, "loanPayment",
                LoanPaymentDto.class);
        accessUtil.maskedValue(maskedFieldsResponse, loanPayment);
        return ResponseEntity.ok(loanPayment);
    }

    private List<AccessDto> getAccess(String role) {
        AccessRequestDto accessRequestDto = new AccessRequestDto();
        accessRequestDto.setRole(role);
        accessRequestDto.setVersion(accessVersion);
        ResponseEntity<AccessDto[]> responseEntity = restTemplate
                .postForEntity(accessUrl, accessRequestDto, AccessDto[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}
