package account.operations;

import account.operations.result.OperationResult;

import java.util.UUID;

/**
 * Закрыть счет.
 *
 * @param accountId идентификатор счета
 */
public record CloseAccount(UUID accountId) implements Operation {
    @Override
    public OperationResult execute() {
        return null;
    }
}
