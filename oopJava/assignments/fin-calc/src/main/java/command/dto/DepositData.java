package command.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * dto команды deposit.
 */
public record DepositData(UUID accountId, BigDecimal amount) {
    public static final String COMMAND_TYPE = "deposit";
}
