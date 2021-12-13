package ru.filit.mdma.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.util.MappingUtil;
import ru.filit.mdma.dm.web.dto.OperationDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = MappingUtil.class)
public interface OperationMapper {

    @Mapping(target = "amount", qualifiedByName = "convertAmount")
    @Mapping(target = "operDate", qualifiedByName = "convertToStringDateTime")
    OperationDto getOperationDto(Operation operation);

    List<OperationDto> getOperationDtos(List<Operation> operations);
}
