package command.dto;

import account.operations.amount.Amount;

import java.util.UUID; 

/**
 * dto команды deposit.
 */
public record DepositData(UUID accountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "deposit";

    public UUID getAccountId() {
        return accountId;
    }
    public String getAmount() {
        return amount;
    }
}
