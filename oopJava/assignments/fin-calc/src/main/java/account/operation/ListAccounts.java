package account.operation;

import account.system.Account;
import account.system.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            List<Account> accountList = accountService.getAllAccounts();
            return new Success(
                    accountList.stream()
                            .map(Account::getId)
                            .map(UUID::toString)
                            .collect(Collectors.joining(" ")), 
                    Optional.empty(), 
                    this.getClass().getSimpleName());
        } catch (Exception e) {
            return new Failure(e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
