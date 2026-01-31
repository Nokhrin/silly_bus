package command.dto;

import account.operations.amount.Amount;

import java.util.UUID; 

/**
 * dto команды transfer.
 */
public record TransferData(UUID sourceAccountId, UUID targetAccountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "transfer";

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }
    public UUID getTargetAccountId() {
        return targetAccountId;
    }
    public String getAmount() {
        return amount;
    }
}
