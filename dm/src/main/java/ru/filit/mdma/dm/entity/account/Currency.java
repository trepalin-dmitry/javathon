package ru.filit.mdma.dm.entity.account;

public enum Currency {

    RUR("RUR");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
