package ru.filit.mdma.crm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.filit.mdma.crm.web.AuthUser;
import ru.filit.mdma.crm.web.dto.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@PropertySource("classpath:config/app.properties")
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClientApi {

    @Value("${dm.url}")
    private String url;
    private final RestTemplate restTemplate;

    @Autowired
    public ClientApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/find")
    public ResponseEntity<List<ClientDto>> findClient(@AuthenticationPrincipal AuthUser user,
                                                      @RequestBody ClientSearchDto clientSearchDto) {
        HttpEntity<ClientSearchDto> httpEntity = getHttpEntity(user, clientSearchDto);
        ResponseEntity<ClientDto[]> responseEntity = restTemplate
                .postForEntity(url, httpEntity, ClientDto[].class);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(responseEntity.getBody())));
    }

    @PostMapping
    public ResponseEntity<ClientDto> getClient(@AuthenticationPrincipal AuthUser user,
                                               @Valid @RequestBody ClientIdDto clientIdDto) {
        ClientSearchDto clientSearchDto = new ClientSearchDto();
        clientSearchDto.setId(clientIdDto.getId());
        ResponseEntity<List<ClientDto>> responseClient = findClient(user, clientSearchDto);
        HttpEntity<ClientIdDto> httpEntity = getHttpEntity(user, clientIdDto);
        ResponseEntity<ContactDto[]> responseContact = restTemplate
                .postForEntity(url + "/contact", httpEntity, ContactDto[].class);
        ResponseEntity<AccountDto[]> responseAccount = restTemplate
                .postForEntity(url + "/account", httpEntity, AccountDto[].class);
        ClientDto clientDto = Objects.requireNonNull(responseClient.getBody()).get(0);
        clientDto.setContacts(Arrays.asList(Objects.requireNonNull(responseContact.getBody())));
        List<AccountDto> accountDtos = setAccountBalance(user, Objects.requireNonNull(responseAccount.getBody()));
        clientDto.setAccounts(accountDtos);
        return ResponseEntity.ok(clientDto);
    }

    @PostMapping("/level")
    public ResponseEntity<ClientLevelDto> getClientLevel(@AuthenticationPrincipal AuthUser user,
                                                         @Valid @RequestBody ClientIdDto clientIdDto) {
        HttpEntity<ClientIdDto> httpEntity = getHttpEntity(user, clientIdDto);
        ResponseEntity<ClientLevelDto> responseEntity = restTemplate
                .postForEntity(url + "/level", httpEntity, ClientLevelDto.class);
        return ResponseEntity.ok(responseEntity.getBody());
    }

    @PostMapping("/account/last-operations")
    public ResponseEntity<List<OperationDto>> getLastOperations(@AuthenticationPrincipal AuthUser user,
                                                                @Valid @RequestBody AccountNumberDto accountNumberDto) {
        OperationSearchDto operationSearchDto = new OperationSearchDto();
        operationSearchDto.setAccountNumber(accountNumberDto.getAccountNumber());
        operationSearchDto.setQuantity("3");
        HttpEntity<OperationSearchDto> httpEntity = getHttpEntity(user, operationSearchDto);
        ResponseEntity<OperationDto[]> responseEntity = restTemplate
                .postForEntity(url + "/account/operation", httpEntity, OperationDto[].class);
        return ResponseEntity.ok(Arrays.asList(Objects.requireNonNull(responseEntity.getBody())));
    }

    @PostMapping("/account/loan-payment")
    public ResponseEntity<LoanPaymentDto> getLoanPayment(@AuthenticationPrincipal AuthUser user,
                                                         @Valid @RequestBody AccountNumberDto accountNumberDto) {
        HttpEntity<AccountNumberDto> httpEntity = getHttpEntity(user, accountNumberDto);
        ResponseEntity<LoanPaymentDto> responseEntity = restTemplate
                .postForEntity(url + "/account/loan-payment", httpEntity, LoanPaymentDto.class);
        return ResponseEntity.ok(responseEntity.getBody());
    }

    @PostMapping("/contact/save")
    public ResponseEntity<ContactDto> saveContact(@AuthenticationPrincipal AuthUser user,
                                                  @Valid @RequestBody ContactDto contactDto) {
        HttpEntity<ContactDto> httpEntity = getHttpEntity(user, contactDto);
        ResponseEntity<ContactDto> responseEntity = restTemplate
                .postForEntity(url + "/contact/save", httpEntity, ContactDto.class);
        return ResponseEntity.ok(responseEntity.getBody());
    }

    private List<AccountDto> setAccountBalance(AuthUser user, AccountDto[] accountDtos) {
        List<AccountDto> accounts = new ArrayList<>();
        for (AccountDto accountDto : accountDtos) {
            AccountNumberDto accountNumberDto = new AccountNumberDto();
            accountNumberDto.setAccountNumber(accountDto.getNumber());
            HttpEntity<AccountNumberDto> httpEntity = getHttpEntity(user, accountNumberDto);
            ResponseEntity<CurrentBalanceDto> responseBalance = restTemplate
                    .postForEntity(url + "/account/balance", httpEntity, CurrentBalanceDto.class);
            accountDto.setBalanceAmount(Objects.requireNonNull(responseBalance.getBody()).getBalanceAmount());
            accounts.add(accountDto);
        }
        return accounts;
    }

    private <T> HttpEntity<T> getHttpEntity(AuthUser user, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("CRM-User-Role", user.getRole().getValue());
        return new HttpEntity<>(body, headers);
    }
}
