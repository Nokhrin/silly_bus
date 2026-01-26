package account.operation;

import account.system.Account;
import account.system.AccountService;

import java.util.Optional;
import java.util.UUID;

/**
 * Закрыть счет.
 *
 * @param accountId идентификатор счета
 */
public record CloseAccount(UUID accountId) implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            return new Success(Optional.empty());
        } catch (Exception e) {
            return new Failure(e.getMessage());
        }
    }
}
