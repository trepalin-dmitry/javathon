package ru.filit.mdma.dm.entity.account;

public enum AccountStatus {

    INACTIVE("INACTIVE"),
    ACTIVE("ACTIVE"),
    LOCKED("LOCKED"),
    CLOSED("CLOSED");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
