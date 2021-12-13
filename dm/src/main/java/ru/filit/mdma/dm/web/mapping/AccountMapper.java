package ru.filit.mdma.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.filit.mdma.dm.entity.account.Account;
import ru.filit.mdma.dm.entity.account.AccountBalance;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.AccountDto;
import ru.filit.mdma.dm.web.dto.CurrentBalanceDto;
import ru.filit.mdma.dm.web.dto.LoanPaymentDto;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface AccountMapper {

    @Mapping(source = "number", target = "shortcut", qualifiedByName = "getShortcutAccountNumber")
    @Mapping(target = "openDate", qualifiedByName = "convertToLocalDate")
    AccountDto getAccountDto(Account account);

    List<AccountDto> getAccountDtos(List<Account> accounts);

    @Mapping(source = "amount", target = "balanceAmount", qualifiedByName = "convertAmount")
    CurrentBalanceDto getCurrentBalanceDto(AccountBalance accountBalance);

    @Mapping(target = "amount", qualifiedByName = "convertAmount")
    LoanPaymentDto getLoanPaymentDto(BigDecimal amount);
}
