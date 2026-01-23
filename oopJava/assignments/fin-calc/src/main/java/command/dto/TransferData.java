package command.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * dto команды transfer.
 */
public record TransferData(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount) {
    public static final String COMMAND_TYPE = "transfer";
}
