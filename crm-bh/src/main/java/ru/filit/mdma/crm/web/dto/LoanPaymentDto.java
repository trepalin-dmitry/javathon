package ru.filit.mdma.crm.web.dto;

import java.util.Objects;

public class LoanPaymentDto {

    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPaymentDto that = (LoanPaymentDto) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "LoanPaymentDto{" +
                "amount='" + amount + '\'' +
                '}';
    }
}
