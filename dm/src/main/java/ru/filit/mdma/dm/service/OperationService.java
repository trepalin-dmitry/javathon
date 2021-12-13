package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.repository.OperationRepository;
import ru.filit.mdma.dm.web.dto.OperationDto;
import ru.filit.mdma.dm.web.dto.OperationSearchDto;
import ru.filit.mdma.dm.web.mapping.OperationMapper;

import java.util.List;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Autowired
    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    public List<OperationDto> getAccountOperations(OperationSearchDto operationSearchDto) {
        List<Operation> operations = operationRepository.getAccountOperations(operationSearchDto.getAccountNumber(),
                operationSearchDto.getQuantity());
        return operationMapper.getOperationDtos(operations);
    }
}
