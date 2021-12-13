package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.entity.account.Account;
import ru.filit.mdma.dm.entity.account.AccountType;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.entity.operation.OperationType;
import ru.filit.mdma.dm.repository.AccountRepository;
import ru.filit.mdma.dm.repository.OperationRepository;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.AccountDto;
import ru.filit.mdma.dm.web.dto.AccountNumberDto;
import ru.filit.mdma.dm.web.dto.ClientIdDto;
import ru.filit.mdma.dm.web.dto.LoanPaymentDto;
import ru.filit.mdma.dm.web.mapping.AccountMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, OperationRepository operationRepository,
                          AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.accountMapper = accountMapper;
    }

    public List<AccountDto> getAccount(ClientIdDto clientIdDto) {
        List<Account> accounts = accountRepository.getAccount(clientIdDto.getId());
        return accountMapper.getAccountDtos(accounts);
    }

    public LoanPaymentDto getLoanPayment(AccountNumberDto accountNumberDto) {
        String accountNumber = accountNumberDto.getAccountNumber();
        Account account = accountRepository.getAccountByNumber(accountNumber);
        if (Objects.isNull(account) || !account.getType().equals(AccountType.OVERDRAFT)) {
            return null;
        }

        List<Operation> operations = operationRepository.getAccountOperationsBetweenDates(
                accountNumber, MappingUtil.convertToLocalDate(account.getOpenDate()), LocalDate.now());
        BigDecimal amount = new BigDecimal(0);
        if (operations.isEmpty()) {
            return accountMapper.getLoanPaymentDto(amount);
        }

        operations.sort(Comparator.comparing(Operation::getOperDate));
        LocalDate dateOriginDebt = null;
        LocalDate defermentPeriodEndDate = LocalDate.MIN;
        LocalDate lastOperDate = LocalDate.MAX;
        boolean balancePositive = true;
        Map<LocalDate, BigDecimal> interestPayments = new HashMap<>();
        for (Operation operation : operations) {
            lastOperDate = MappingUtil.convertToLocalDate(operation.getOperDate());
            if (!balancePositive) {
                if (defermentPeriodEndDate.isBefore(lastOperDate)
                        || defermentPeriodEndDate.compareTo(lastOperDate) == 0) {
                    long quantityDays = getQuantityDaysBetweenDates(defermentPeriodEndDate, lastOperDate);
                    setInterestPayments(interestPayments, amount, quantityDays, defermentPeriodEndDate);
                }
            }
            if (operation.getType().equals(OperationType.RECEIPT)) {
                amount = amount.add(operation.getAmount());
                if (!balancePositive && amount.compareTo(BigDecimal.ZERO) >= 0) {
                    balancePositive = true;
                    dateOriginDebt = null;
                }
            } else {
                amount = amount.subtract(operation.getAmount());
                if (amount.compareTo(BigDecimal.ZERO) < 0 && Objects.isNull(dateOriginDebt)) {
                    dateOriginDebt = lastOperDate;
                    defermentPeriodEndDate = getEndOfDefermentPeriod(dateOriginDebt, account.getDeferment());
                    balancePositive = false;
                }
            }
        }

        lastOperDate = lastOperDate.plusDays(1);
        if (amount.compareTo(BigDecimal.ZERO) < 0 && lastOperDate.isBefore(LocalDate.now())) {
            if (defermentPeriodEndDate.isAfter(lastOperDate)) {
                lastOperDate = defermentPeriodEndDate;
            }
            long quantityDays = getQuantityDaysBetweenDates(lastOperDate, LocalDate.now());
            setInterestPayments(interestPayments, amount, quantityDays, lastOperDate);
        }

        BigDecimal amountOfPayments = new BigDecimal(0);
        for (BigDecimal payment : interestPayments.values()) {
            amountOfPayments = amountOfPayments.add(payment);
        }

        return accountMapper.getLoanPaymentDto(amountOfPayments);
    }

    private void setInterestPayments(Map<LocalDate, BigDecimal> interestPayments, BigDecimal amount,
                                     long quantityDays, LocalDate date) {
        BigDecimal debt = new BigDecimal(0);
        for (int i = 0; i < quantityDays; i++) {
            debt = debt.add(amount.multiply(BigDecimal.valueOf(0.07))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN));
            amount = amount.add(debt).setScale(8, RoundingMode.HALF_DOWN);
            interestPayments.put(date.plusDays(i), debt.negate());
            debt = BigDecimal.ZERO;
        }
    }

    private long getQuantityDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1)).count();
    }

    private LocalDate getEndOfDefermentPeriod(LocalDate startDate, int defermentPeriod) {
        LocalDate endDate = startDate.plusDays(defermentPeriod);
        for (int i = 0; i < defermentPeriod; i++) {
            if (startDate.getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue()
                    || startDate.getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue()) {
                endDate = endDate.plusDays(1);
            }
            startDate = startDate.plusDays(1);
        }
        return endDate;
    }
}
