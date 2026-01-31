package account.operations;

import account.operations.amount.Amount;
import account.operations.result.OperationResult;

import java.util.UUID;

/**
 * Снять со счета.
 *
 * @param accountId идентификатор счета
 * @param amount    сумма снятия
 */
public record Withdraw(UUID accountId, Amount amount) implements Operation {
    @Override
    public OperationResult execute() {
        return null;
    }
}
