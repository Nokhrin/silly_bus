package account.operations;

import account.operations.amount.Amount;
import account.operations.result.OperationResult;

import java.util.UUID; 
/**
 * Зачислить на счет.
 * @param accountId идентификатор счета
 * @param amount    сумма зачисления
 */
public record Deposit(
        UUID accountId,
        Amount amount
) implements Operation {
    @Override
    public OperationResult execute() {
        return null;
    }
}
