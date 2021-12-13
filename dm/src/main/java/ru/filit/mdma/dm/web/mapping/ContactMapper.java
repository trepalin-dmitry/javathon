package ru.filit.mdma.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.filit.mdma.dm.entity.client.Contact;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.ContactDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface ContactMapper {

    @Mapping(source = ".", target = "shortcut", qualifiedByName = "getShortcutContactValue")
    ContactDto getContactDto(Contact contact);

    Contact asEntity(ContactDto contactDto);

    List<ContactDto> getContactsDto(List<Contact> contacts);
}
