package account.operations;

import account.operations.amount.Amount;
import account.operations.result.OperationResult;

import java.util.UUID;

/**
 * Перевести со счета на счет.
 *
 * @param sourceAccountId идентификатор счета отправителя
 * @param targetAccountId идентификатор счета получателя
 * @param amount          сумма перевода
 */
public record Transfer(UUID sourceAccountId, UUID targetAccountId, Amount amount) implements Operation {
    @Override
    public OperationResult execute() {
        return null;
    }
}
