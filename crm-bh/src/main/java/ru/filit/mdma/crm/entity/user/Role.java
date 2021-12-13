package ru.filit.mdma.crm.entity.user;

public enum Role {

    MANAGER("MANAGER"),
    SUPERVISOR("SUPERVISOR"),
    AUDITOR("AUDITOR");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
