package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.account.Account;

import java.util.List;

public interface AccountRepository {

    Account getAccountByNumber(String accountNumber);

    List<Account> getAccount(String clientId);
}
