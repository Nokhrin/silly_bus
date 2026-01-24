package command.dto;

import account.system.Amount;

import java.util.UUID; /**
 * dto команды withdraw.
 */
public record WithdrawData(UUID accountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "withdraw";

    public Amount toAmount() {
        return Amount.of(amount);
    }

}
