package ru.filit.mdma.dm.entity.client;

public enum ClientLevel {

    LOW("LOW"),
    MIDDLE("MIDDLE"),
    SILVER("SILVER"),
    GOLD("GOLD");

    private final String value;

    ClientLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
