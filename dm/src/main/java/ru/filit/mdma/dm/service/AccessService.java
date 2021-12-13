package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.filit.mdma.dm.entity.user.Access;
import ru.filit.mdma.dm.repository.AccessRepository;
import ru.filit.mdma.dm.web.dto.AccessDto;
import ru.filit.mdma.dm.web.mapping.AccessMapper;

import java.util.List;

@Service
public class AccessService {

    private final AccessRepository accessRepository;
    private final AccessMapper accessMapper;

    @Autowired
    public AccessService(AccessRepository accessRepository, AccessMapper accessMapper) {
        this.accessRepository = accessRepository;
        this.accessMapper = accessMapper;
    }

    public List<AccessDto> getAccess(String version, String role) {
        int ver;
        try {
            ver = Integer.parseInt(version);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Access> accesses = accessRepository.getAccess(ver, role);
        return accessMapper.getAccessDtos(accesses);
    }
}
