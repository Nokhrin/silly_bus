package command.dto;

import account.system.Amount;

import java.util.UUID; /**
 * dto команды withdraw.
 */
public record WithdrawData(UUID accountId, Amount amount) implements CommandData {
    public static final String COMMAND_TYPE = "withdraw";
}
