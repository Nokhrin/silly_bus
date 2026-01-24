package command.dto;

import account.system.Amount;

import java.util.UUID; /**
 * dto команды transfer.
 */
public record TransferData(UUID sourceAccountId, UUID targetAccountId, Amount amount) implements CommandData {
    public static final String COMMAND_TYPE = "transfer";
}
