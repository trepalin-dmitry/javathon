package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.account.AccountBalance;

import java.time.LocalDate;

public interface BalanceRepository {

    AccountBalance getAccountBalance(String accountNumber, LocalDate localDate);
}
