package ru.filit.mdma.dms.web.dto;

import java.util.Objects;

public class ClientDto {

    private String id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String birthDate;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(id, clientDto.id)
                && Objects.equals(lastname, clientDto.lastname)
                && Objects.equals(firstname, clientDto.firstname)
                && Objects.equals(patronymic, clientDto.patronymic)
                && Objects.equals(birthDate, clientDto.birthDate)
                && Objects.equals(passportSeries, clientDto.passportSeries)
                && Objects.equals(passportNumber, clientDto.passportNumber)
                && Objects.equals(inn, clientDto.inn)
                && Objects.equals(address, clientDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, patronymic, birthDate, passportSeries,
                passportNumber, inn, address);
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id='" + id + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", inn='" + inn + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
