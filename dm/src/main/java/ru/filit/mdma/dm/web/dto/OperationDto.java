package ru.filit.mdma.dm.web.dto;

import java.util.Objects;

public class OperationDto {

    private String type;
    private String accountNumber;
    private String operDate;
    private String amount;
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDto that = (OperationDto) o;
        return Objects.equals(type, that.type)
                && Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(operDate, that.operDate)
                && Objects.equals(amount, that.amount)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, accountNumber, operDate, amount, description);
    }

    @Override
    public String toString() {
        return "OperationDto{" +
                "type='" + type + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", operDate='" + operDate + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
