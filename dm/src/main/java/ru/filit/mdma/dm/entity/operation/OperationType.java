package ru.filit.mdma.dm.entity.operation;

public enum OperationType {

    RECEIPT("RECEIPT"),
    EXPENSE("EXPENSE");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
