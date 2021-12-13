package ru.filit.mdma.dm.entity.account;

public enum AccountType {

    PAYMENT("PAYMENT"),
    BUDGET("BUDGET"),
    TRANSIT("TRANSIT"),
    OVERDRAFT("OVERDRAFT");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
