package ru.filit.mdma.crm.web.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AccountNumberDto {

    @NotBlank
    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumberDto that = (AccountNumberDto) o;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "AccountNumberDto{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
