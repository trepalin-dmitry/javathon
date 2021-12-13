package ru.filit.mdma.dm.repository;

import ru.filit.mdma.dm.entity.operation.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepository {

    List<Operation> getAccountOperations(String accountNumber, String quantity);

    List<Operation> getAccountOperationsByMonth(String accountNumber, LocalDate localDate);

    List<Operation> getAccountOperationsBetweenDates(String accountNumber, LocalDate startDate, LocalDate endDate);
}
