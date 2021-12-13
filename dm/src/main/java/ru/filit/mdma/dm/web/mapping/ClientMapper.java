package ru.filit.mdma.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.filit.mdma.dm.entity.client.Client;
import ru.filit.mdma.dm.entity.client.ClientLevel;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.ClientDto;
import ru.filit.mdma.dm.web.dto.ClientLevelDto;
import ru.filit.mdma.dm.web.dto.ClientSearchDto;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface ClientMapper {

    @Mapping(target = "birthDate", qualifiedByName = "convertToLocalDate")
    ClientDto getClientDto(Client client);

    List<ClientDto> getClientDtos(List<Client> clients);

    ClientLevelDto getClientLevelDto(ClientLevel level, String accountNumber, BigDecimal avgBalance);

    @Mapping(source = "passport", target = "passportSeries", qualifiedByName = "getPassportSeries")
    @Mapping(source = "passport", target = "passportNumber", qualifiedByName = "getPassportNumber")
    @Mapping(target = "birthDate", qualifiedByName = "convertToStringDate")
    Client asEntity(ClientSearchDto clientSearchDto);
}
