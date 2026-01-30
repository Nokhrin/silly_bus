package account.operation;

import account.system.Amount;

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
