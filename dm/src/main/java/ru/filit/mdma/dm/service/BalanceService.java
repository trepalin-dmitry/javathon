package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.entity.account.AccountBalance;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.entity.operation.OperationType;
import ru.filit.mdma.dm.repository.BalanceRepository;
import ru.filit.mdma.dm.repository.OperationRepository;
import ru.filit.mdma.dm.web.dto.CurrentBalanceDto;
import ru.filit.mdma.dm.web.mapping.AccountMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final OperationRepository operationRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository, OperationRepository operationRepository,
                          AccountMapper accountMapper) {
        this.balanceRepository = balanceRepository;
        this.operationRepository = operationRepository;
        this.accountMapper = accountMapper;
    }

    public CurrentBalanceDto getAccountBalance(String accountNumber, LocalDate localDate) {
        AccountBalance accountBalance = balanceRepository.getAccountBalance(accountNumber, localDate);
        if (Objects.isNull(accountBalance)) {
            return null;
        }
        List<Operation> operations = operationRepository
                .getAccountOperationsByMonth(accountNumber, localDate);
        for (Operation operation : operations) {
            BigDecimal amount;
            if (operation.getType().equals(OperationType.RECEIPT)) {
                amount = accountBalance.getAmount().add(operation.getAmount());
            } else {
                amount = accountBalance.getAmount().subtract(operation.getAmount());
            }
            accountBalance.setAmount(amount);
        }
        return accountMapper.getCurrentBalanceDto(accountBalance);
    }
}
