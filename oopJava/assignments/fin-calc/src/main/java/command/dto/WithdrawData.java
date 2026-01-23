package command.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * dto команды withdraw.
 */
public record WithdrawData(UUID accountId, BigDecimal amount) {
    public static final String COMMAND_TYPE = "withdraw";
}
