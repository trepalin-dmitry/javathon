package ru.filit.mdma.dm.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.filit.mdma.dm.entity.operation.Operation;
import ru.filit.mdma.dm.repository.OperationRepository;
import ru.filit.mdma.dm.util.MappingUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:config/app.properties")
public class OperationRepositoryImpl implements OperationRepository {

    private final Resource resource;
    private final ObjectMapper objectMapperYaml;
    private Operation[] operationsYaml;

    @Autowired
    public OperationRepositoryImpl(@Value("${operations}") String location, ResourceLoader resourceLoader,
                                   @Qualifier("objectMapperYaml") ObjectMapper objectMapperYaml) {
        this.resource = resourceLoader.getResource(location);
        this.objectMapperYaml = objectMapperYaml;
    }

    @PostConstruct
    private void loadOperationData() {
        try {
            operationsYaml = objectMapperYaml.readValue(resource.getFile(), Operation[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Operation> getAccountOperations(String accountNumber, String quantity) {
        return Arrays.stream(operationsYaml)
                .filter(operation -> operation.getAccountNumber().equals(accountNumber))
                .sorted(Comparator.comparing(Operation::getOperDate).reversed())
                .limit(Long.parseLong(quantity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getAccountOperationsByMonth(String accountNumber, LocalDate localDate) {
        List<Operation> operations = new ArrayList<>();
        for (Operation operation : operationsYaml) {
            if (operation.getAccountNumber().equals(accountNumber)) {
                LocalDate operDate = MappingUtil.convertToLocalDate(operation.getOperDate());
                if (operDate.getMonthValue() == localDate.getMonthValue() && operDate.compareTo(localDate) <= 0) {
                    operations.add(operation);
                }
            }
        }
        return operations;
    }

    @Override
    public List<Operation> getAccountOperationsBetweenDates(String accountNumber,
                                                            LocalDate startDate, LocalDate endDate) {
        List<Operation> operations = new ArrayList<>();
        for (Operation operation : operationsYaml) {
            if (operation.getAccountNumber().equals(accountNumber)) {
                LocalDate operDate = MappingUtil.convertToLocalDate(operation.getOperDate());
                if (operDate.compareTo(startDate) >= 0 && operDate.compareTo(endDate) <= 0) {
                    operations.add(operation);
                }
            }
        }
        return operations;
    }
}
