package command.dto;

import account.system.Amount;

import java.util.UUID; /**
 * dto команды deposit.
 */
public record DepositData(UUID accountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "deposit";
    
    public Amount toAmount() {
        return Amount.of(amount);
    }
}
