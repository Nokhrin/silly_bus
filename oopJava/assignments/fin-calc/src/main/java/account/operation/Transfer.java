package account.operation;

import account.system.Amount;

import java.util.Optional;
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
