package ru.filit.mdma.dm.web.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AccessRequestDto {

    @NotBlank
    private String role;
    @NotBlank
    private String version;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessRequestDto that = (AccessRequestDto) o;
        return Objects.equals(role, that.role)
                && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, version);
    }

    @Override
    public String toString() {
        return "AccessRequestDto{" +
                "role='" + role + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
