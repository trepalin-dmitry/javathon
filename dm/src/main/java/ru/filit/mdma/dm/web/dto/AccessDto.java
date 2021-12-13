package ru.filit.mdma.dm.web.dto;

import java.util.Objects;

public class AccessDto {

    private String entity;
    private String property;

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
        AccessDto accessDto = (AccessDto) o;
        return Objects.equals(entity, accessDto.entity)
                && Objects.equals(property, accessDto.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, property);
    }

    @Override
    public String toString() {
        return "AccessDto{" +
                "entity='" + entity + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}
