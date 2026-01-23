package command.dto;

import java.util.UUID;

/**
 * dto команды balance.
 */
public record BalanceData(UUID accountId) {
    public static final String COMMAND_TYPE = "balance";
}
