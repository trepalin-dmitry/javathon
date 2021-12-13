package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.entity.account.Account;
import ru.filit.mdma.dm.entity.account.AccountStatus;
import ru.filit.mdma.dm.entity.client.Client;
import ru.filit.mdma.dm.entity.client.ClientLevel;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.entity.operation.OperationType;
import ru.filit.mdma.dm.repository.AccountRepository;
import ru.filit.mdma.dm.repository.ClientRepository;
import ru.filit.mdma.dm.repository.OperationRepository;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.*;
import ru.filit.mdma.dm.web.exception.ClientNotFoundException;
import ru.filit.mdma.dm.web.mapping.ClientMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final BalanceService balanceService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, AccountRepository accountRepository,
                         OperationRepository operationRepository, BalanceService balanceService,
                         ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.balanceService = balanceService;
        this.clientMapper = clientMapper;
    }

    public List<ClientDto> getClient(ClientSearchDto clientSearchDto) {
        Client client = clientMapper.asEntity(clientSearchDto);
        List<Client> clients = clientRepository.getClients(client);
        if (clients.isEmpty() && Objects.nonNull(clientSearchDto.getId())) {
            throw new ClientNotFoundException("client with id " + clientSearchDto.getId() + " not found");
        }
        return clientMapper.getClientDtos(clients);
    }

    public ClientLevelDto getClientLevel(ClientIdDto clientIdDto) {
        clientRepository.getClient(clientIdDto.getId());
        List<Account> accounts = accountRepository.getAccount(clientIdDto.getId());
        LocalDate startDate = LocalDate.now().minusDays(30);
        Map<Account, List<Operation>> accountOperationsMap = getAccountOperationsMap(accounts, startDate);

        Map<Account, Map<LocalDate, BigDecimal>> accountAmountPerDateMap = new HashMap<>();
        for (Map.Entry<Account, List<Operation>> entry : accountOperationsMap.entrySet()) {
            Account account = entry.getKey();
            if (account.getStatus().equals(AccountStatus.CLOSED) &&
                    MappingUtil.convertToLocalDate(account.getCloseDate()).isBefore(startDate)) {
                continue;
            }
            CurrentBalanceDto currentBalanceDto = balanceService.getAccountBalance(
                    entry.getKey().getNumber(), startDate);
            BigDecimal amount = new BigDecimal(currentBalanceDto.getBalanceAmount());
            List<Operation> operations = entry.getValue();
            Map<LocalDate, BigDecimal> amountPerDateMap = getAmountPerDateMap(operations, startDate, amount);
            accountAmountPerDateMap.put(entry.getKey(), amountPerDateMap);
        }
        if (accountAmountPerDateMap.isEmpty()) {
            return null;
        }

        Map<BigDecimal, Account> avgAccountAmountMap = new HashMap<>();
        BigDecimal maxAvgAmount = new BigDecimal(0);
        for (Map.Entry<Account, Map<LocalDate, BigDecimal>> entry : accountAmountPerDateMap.entrySet()) {
            BigDecimal avgAmount = new BigDecimal(0);
            for (Map.Entry<LocalDate, BigDecimal> amountPerDate : entry.getValue().entrySet()) {
                avgAmount = avgAmount.add(amountPerDate.getValue());
            }
            avgAmount = avgAmount.divide(BigDecimal.valueOf(entry.getValue().size()), RoundingMode.HALF_DOWN);
            if (maxAvgAmount.compareTo(avgAmount) < 0) {
                maxAvgAmount = avgAmount;
            }
            avgAccountAmountMap.put(avgAmount, entry.getKey());
        }
        Account account = avgAccountAmountMap.get(maxAvgAmount);
        return clientMapper.getClientLevelDto(getClientLevelValue(maxAvgAmount), account.getNumber(), maxAvgAmount);
    }

    private Map<Account, List<Operation>> getAccountOperationsMap(List<Account> accounts, LocalDate startDate) {
        Map<Account, List<Operation>> accountOperationsMap = new HashMap<>();
        for (Account account : accounts) {
            LocalDate openDate = MappingUtil.convertToLocalDate(account.getOpenDate());
            if (openDate.compareTo(startDate) > 0) {
                startDate = openDate;
            }
            accountOperationsMap.put(account, operationRepository.getAccountOperationsBetweenDates(
                    account.getNumber(), startDate, LocalDate.now()));
        }
        return accountOperationsMap;
    }

    private Map<LocalDate, BigDecimal> getAmountPerDateMap(List<Operation> operations, LocalDate startDate,
                                                           BigDecimal amount) {
        Map<LocalDate, BigDecimal> amountPerDateMap = new HashMap<>();
        if (operations.isEmpty()) {
            amountPerDateMap.put(startDate, amount);
        } else {
            for (Operation operation : operations) {
                if (operation.getType().equals(OperationType.RECEIPT)) {
                    amount = amount.add(operation.getAmount());
                } else {
                    amount = amount.subtract(operation.getAmount());
                }
                amountPerDateMap.put(MappingUtil.convertToLocalDate(operation.getOperDate()), amount);
            }
        }
        return amountPerDateMap;
    }

    private ClientLevel getClientLevelValue(BigDecimal amount) {
        int value;
        if (amount.compareTo(BigDecimal.valueOf(1000000)) >= 0) {
            return ClientLevel.GOLD;
        } else {
            value = amount.intValue();
        }
        if (value < 30000) {
            return ClientLevel.LOW;
        } else if (value < 300000) {
            return ClientLevel.MIDDLE;
        } else {
            return ClientLevel.SILVER;
        }
    }
}
