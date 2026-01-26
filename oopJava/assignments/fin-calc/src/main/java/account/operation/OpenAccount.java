package account.operation;

import account.system.Account;
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
            Account account = accountService.getAccount(accountId);
            return new Success(Optional.of(account));
        } catch (Exception e) {
            return new Failure(e.getMessage());
        }
    }

}
