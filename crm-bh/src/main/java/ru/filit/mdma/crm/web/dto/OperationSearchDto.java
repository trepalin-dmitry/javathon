package ru.filit.mdma.crm.web.dto;

import java.util.Objects;

public class OperationSearchDto {

    private String accountNumber;
    private String quantity;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationSearchDto that = (OperationSearchDto) o;
        return Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, quantity);
    }

    @Override
    public String toString() {
        return "OperationSearchDto{" +
                "accountNumber='" + accountNumber + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
