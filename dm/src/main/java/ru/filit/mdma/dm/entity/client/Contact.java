package ru.filit.mdma.dm.entity.client;

import java.util.Objects;

public class Contact {

    private String id;
    private String clientId;
    private ContactType type;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id)
                && Objects.equals(clientId, contact.clientId)
                && type == contact.type
                && Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, type, value);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
