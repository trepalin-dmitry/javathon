package ru.filit.mdma.dms.web.dto;

import java.util.Objects;

public class CurrentBalanceDto {

    private String balanceAmount;

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentBalanceDto that = (CurrentBalanceDto) o;
        return Objects.equals(balanceAmount, that.balanceAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceAmount);
    }

    @Override
    public String toString() {
        return "CurrentBalanceDto{" +
                "balanceAmount='" + balanceAmount + '\'' +
                '}';
    }
}
