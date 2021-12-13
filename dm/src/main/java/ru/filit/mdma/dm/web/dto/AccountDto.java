package ru.filit.mdma.dm.web.dto;

import java.util.Objects;

public class AccountDto {

    private String number;
    private String clientId;
    private String type;
    private String currency;
    private String status;
    private String openDate;
    private String closeDate;
    private String deferment;
    private String shortcut;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getDeferment() {
        return deferment;
    }

    public void setDeferment(String deferment) {
        this.deferment = deferment;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(number, that.number)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(type, that.type)
                && Objects.equals(currency, that.currency)
                && Objects.equals(status, that.status)
                && Objects.equals(openDate, that.openDate)
                && Objects.equals(closeDate, that.closeDate)
                && Objects.equals(deferment, that.deferment)
                && Objects.equals(shortcut, that.shortcut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, clientId, type, currency, status, openDate, closeDate, deferment, shortcut);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "number='" + number + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", openDate='" + openDate + '\'' +
                ", closeDate='" + closeDate + '\'' +
                ", deferment='" + deferment + '\'' +
                ", shortcut='" + shortcut + '\'' +
                '}';
    }
}
