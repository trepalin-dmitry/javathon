package ru.filit.mdma.dm.entity.account;

import java.util.Objects;

public class Account {

    private String number;
    private String clientId;
    private AccountType type;
    private Currency currency;
    private AccountStatus status;
    private Long openDate;
    private Long closeDate;
    private Integer deferment;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Long getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Long openDate) {
        this.openDate = openDate;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getDeferment() {
        return deferment;
    }

    public void setDeferment(Integer deferment) {
        this.deferment = deferment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number)
                && Objects.equals(clientId, account.clientId)
                && type == account.type
                && currency == account.currency
                && status == account.status
                && Objects.equals(openDate, account.openDate)
                && Objects.equals(closeDate, account.closeDate)
                && Objects.equals(deferment, account.deferment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, clientId, type, currency, status, openDate, closeDate, deferment);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type=" + type +
                ", currency=" + currency +
                ", status=" + status +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", deferment=" + deferment +
                '}';
    }
}
