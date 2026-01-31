package account.operations;

import account.operations.result.OperationResult;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {

    @Override
    public OperationResult execute() {
        return null;
    }
}
