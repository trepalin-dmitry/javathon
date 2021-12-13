package ru.filit.mdma.crm.web.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ClientIdDto {

    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientIdDto that = (ClientIdDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClientIdDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
