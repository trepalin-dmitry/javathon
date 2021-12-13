package ru.filit.mdma.dm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.dm.service.AccessService;
import ru.filit.mdma.dm.web.dto.AccessDto;
import ru.filit.mdma.dm.web.dto.AccessRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/access", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AccessApi {

    private final AccessService accessService;

    @Autowired
    public AccessApi(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping
    public ResponseEntity<List<AccessDto>> getAccess(@Valid @RequestBody AccessRequestDto accessRequestDto) {
        List<AccessDto> accesses = accessService.getAccess(accessRequestDto.getVersion(), accessRequestDto.getRole());
        return ResponseEntity.ok(accesses);
    }
}
