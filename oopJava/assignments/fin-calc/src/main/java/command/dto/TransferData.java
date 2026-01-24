package command.dto;

import account.system.Amount;

import java.util.UUID; /**
 * dto команды transfer.
 */
public record TransferData(UUID sourceAccountId, UUID targetAccountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "transfer";

    public Amount toAmount() {
        return Amount.of(amount);
    }

}
