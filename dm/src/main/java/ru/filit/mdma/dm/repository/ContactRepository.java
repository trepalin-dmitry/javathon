package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.client.Contact;

import java.util.List;

public interface ContactRepository {

    Contact save(Contact contact);

    List<Contact> getContact(String clientId);
}
