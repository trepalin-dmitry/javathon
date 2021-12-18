package ru.filit.mdma.dm.database;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Balance {
    private String accountNumber;
    private long balanceDate;
    private String amount;
}
