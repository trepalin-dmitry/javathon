package ru.filit.mdma.dm.entity.account;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountBalance {

    private String accountNumber;
    private Long balanceDate;
    private BigDecimal amount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Long balanceDate) {
        this.balanceDate = balanceDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(balanceDate, that.balanceDate)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balanceDate, amount);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balanceDate=" + balanceDate +
                ", amount=" + amount +
                '}';
    }
}
