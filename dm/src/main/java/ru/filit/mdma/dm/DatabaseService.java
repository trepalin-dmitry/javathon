package ru.filit.mdma.dm;

import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.utils.YamlUtils;
import ru.filit.mdma.dm.web.dto.ClientDto;

import java.util.Collection;
import java.util.Map;

@Service
public class DatabaseService {
    public Collection<ClientDto> getClients(){
        return YamlUtils.parse("classpath:datafiles/clients.yml", this::parse);
    }

    private ClientDto parse(Map<String, String> map) {
        ClientDto dto = new ClientDto();
        dto.id(String.valueOf(map.get("id")));
        dto.lastname(String.valueOf(map.get("lastname")));
        dto.firstname(String.valueOf(map.get("firstname")));
        dto.patronymic(String.valueOf(map.get("patronymic")));
        dto.birthDate(String.valueOf(map.get("birthDate")));
        dto.passportSeries(String.valueOf(map.get("passportSeries")));
        dto.passportNumber(String.valueOf(map.get("passportNumber")));
        dto.inn(String.valueOf(map.get("inn")));
        dto.address(String.valueOf(map.get("address")));
        return dto;
    }
}
