package command.dto;

import java.util.UUID;

/**
 * dto команды close.
 */
public record CloseAccountData(UUID accountId) {
    public static final String COMMAND_TYPE = "close";
}
