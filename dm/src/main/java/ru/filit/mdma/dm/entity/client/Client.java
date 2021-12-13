package ru.filit.mdma.dm.entity.client;

import java.util.Objects;

public class Client {

    private String id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private Long birthDate;
    private String passportSeries;
    private String passportNumber;
    private String inn;
    private String address;

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

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
                && Objects.equals(lastname, client.lastname)
                && Objects.equals(firstname, client.firstname)
                && Objects.equals(patronymic, client.patronymic)
                && Objects.equals(birthDate, client.birthDate)
                && Objects.equals(passportSeries, client.passportSeries)
                && Objects.equals(passportNumber, client.passportNumber)
                && Objects.equals(inn, client.inn)
                && Objects.equals(address, client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, patronymic, birthDate, passportSeries,
                passportNumber, inn, address);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", lastName='" + lastname + '\'' +
                ", firstName='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", inn='" + inn + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
