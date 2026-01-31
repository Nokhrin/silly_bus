package command.dto;

import account.operations.amount.Amount;

import java.util.UUID; /**
 * dto команды withdraw.
 */
public record WithdrawData(UUID accountId, String amount) implements CommandData {
    public static final String COMMAND_TYPE = "withdraw";

}
