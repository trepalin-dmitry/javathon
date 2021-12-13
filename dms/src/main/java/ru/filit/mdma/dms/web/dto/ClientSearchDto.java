package ru.filit.mdma.dms.web.dto;

import java.util.Objects;

public class ClientSearchDto {

    private String id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String birthDate;
    private String passport;
    private String inn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientSearchDto that = (ClientSearchDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(lastname, that.lastname)
                && Objects.equals(firstname, that.firstname)
                && Objects.equals(patronymic, that.patronymic)
                && Objects.equals(birthDate, that.birthDate)
                && Objects.equals(passport, that.passport)
                && Objects.equals(inn, that.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, patronymic, birthDate, passport, inn);
    }

    @Override
    public String toString() {
        return "ClientSearchDto{" +
                "id='" + id + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", passport='" + passport + '\'' +
                ", inn='" + inn + '\'' +
                '}';
    }
}