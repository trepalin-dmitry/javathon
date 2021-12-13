package ru.filit.mdma.dm.web.dto;

import java.util.Objects;

public class ClientLevelDto {

    private String level;
    private String accountNumber;
    private String avgBalance;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAvgBalance() {
        return avgBalance;
    }

    public void setAvgBalance(String avgBalance) {
        this.avgBalance = avgBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientLevelDto that = (ClientLevelDto) o;
        return Objects.equals(level, that.level)
                && Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(avgBalance, that.avgBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, accountNumber, avgBalance);
    }

    @Override
    public String toString() {
        return "ClientLevelDto{" +
                "level='" + level + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", avgBalance='" + avgBalance + '\'' +
                '}';
    }
}
