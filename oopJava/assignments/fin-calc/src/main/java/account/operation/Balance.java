package account.operation;

import account.system.Account;
import account.system.AccountRepository;

import java.util.Optional;
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
