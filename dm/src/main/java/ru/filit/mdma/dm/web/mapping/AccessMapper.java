package ru.filit.mdma.dm.web.mapping;

import org.mapstruct.Mapper;
import ru.filit.mdma.dm.entity.user.Access;
import ru.filit.mdma.dm.web.dto.AccessDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccessMapper {

    AccessDto getAccessDto(Access access);

    List<AccessDto> getAccessDtos(List<Access> accesses);
}
