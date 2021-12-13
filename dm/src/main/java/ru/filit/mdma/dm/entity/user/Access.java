package ru.filit.mdma.dm.entity.user;

import java.util.Objects;

public class Access {

    private String role;
    private String entity;
    private String property;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Access access = (Access) o;
        return Objects.equals(role, access.role)
                && Objects.equals(entity, access.entity)
                && Objects.equals(property, access.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, entity, property);
    }

    @Override
    public String toString() {
        return "Access{" +
                "role='" + role + '\'' +
                ", entity='" + entity + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}
