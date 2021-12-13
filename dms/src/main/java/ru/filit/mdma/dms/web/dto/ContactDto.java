package ru.filit.mdma.dms.web.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ContactDto {

    private String id;
    @NotBlank
    private String clientId;
    @NotBlank
    private String type;
    @NotBlank
    private String value;
    private String shortcut;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDto that = (ContactDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(type, that.type)
                && Objects.equals(value, that.value)
                && Objects.equals(shortcut, that.shortcut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, type, value, shortcut);
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", shortcut='" + shortcut + '\'' +
                '}';
    }
}