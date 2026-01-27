package account.operation;

import account.system.AccountService;

import java.util.Optional;
import java.util.UUID;

/**
 * Открыть счет.
 */
public record OpenAccount() implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            UUID accountId = accountService.openAccount();
            return new Success(Optional.of(accountService.getAccount(accountId)), this.getClass().getSimpleName());
        } catch (Exception e) {
            return new Failure(e.getMessage(), this.getClass().getSimpleName());
        }
    }

}
