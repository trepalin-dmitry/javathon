package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.entity.client.Contact;
import ru.filit.mdma.dm.repository.ClientRepository;
import ru.filit.mdma.dm.repository.ContactRepository;
import ru.filit.mdma.dm.web.dto.ClientIdDto;
import ru.filit.mdma.dm.web.dto.ContactDto;
import ru.filit.mdma.dm.web.mapping.ContactMapper;

import java.util.List;
import java.util.Objects;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ClientRepository clientRepository,
                          ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
        this.contactMapper = contactMapper;
    }

    public List<ContactDto> getContact(ClientIdDto clientIdDto) {
        clientRepository.getClient(clientIdDto.getId());
        List<Contact> contacts = contactRepository.getContact(clientIdDto.getId());
        return contactMapper.getContactsDto(contacts);
    }

    public ContactDto saveContact(ContactDto contactDto) {
        String clientId = contactDto.getClientId();
        clientRepository.getClient(clientId);
        Contact contact = contactMapper.asEntity(contactDto);
        contact = contactRepository.save(contact);
        if (Objects.isNull(contact)) {
            return null;
        }
        return contactMapper.getContactDto(contact);
    }
}
