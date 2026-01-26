package account.operation;

import account.system.Account;
import account.system.AccountService;

import java.util.List;
import java.util.Optional;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            List<Account> accountList = accountService.getAllAccounts();
            return new Success(Optional.empty());
        } catch (Exception e) {
            return new Failure(e.getMessage());
        }
    }
}
