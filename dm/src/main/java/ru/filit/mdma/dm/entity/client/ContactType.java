package ru.filit.mdma.dm.entity.client;

public enum ContactType {

    PHONE("PHONE"),
    EMAIL("EMAIL");

    private final String value;

    ContactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
