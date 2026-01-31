package account.operations;

import account.operations.result.OperationResult;

import java.util.UUID;

/**
 * Посмотреть баланс счета.
 *
 * @param accountId идентификатор счета
 */
public record Balance(UUID accountId) implements Operation {

    @Override
    public OperationResult execute() {
        return null;
    }
}
