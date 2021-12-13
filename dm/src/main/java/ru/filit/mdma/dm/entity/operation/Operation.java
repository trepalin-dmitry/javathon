package ru.filit.mdma.dm.entity.operation;

import java.math.BigDecimal;
import java.util.Objects;

public class Operation {

    private OperationType type;
    private String accountNumber;
    private Long operDate;
    private BigDecimal amount;
    private String description;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getOperDate() {
        return operDate;
    }

    public void setOperDate(Long operDate) {
        this.operDate = operDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
        Operation operation = (Operation) o;
        return type == operation.type
                && Objects.equals(accountNumber, operation.accountNumber)
                && Objects.equals(operDate, operation.operDate)
                && Objects.equals(amount, operation.amount)
                && Objects.equals(description, operation.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, accountNumber, operDate, amount, description);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type=" + type +
                ", accountNumber='" + accountNumber + '\'' +
                ", operDate=" + operDate +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
