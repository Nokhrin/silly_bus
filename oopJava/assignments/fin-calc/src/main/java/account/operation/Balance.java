package account.operation;

import account.system.Account;
import account.system.AccountRepository;
import account.system.AccountService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Посмотреть баланс счета.
 *
 * @param accountId идентификатор счета
 */
public record Balance(UUID accountId) implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            Account account = accountService.getAccount(accountId);
            String balance = account.getBalance().toString();
            return new Success(
                    balance,
                    Optional.of(accountService.getAccount(accountId)), 
                    this.getClass().getSimpleName()
            );
        } catch (Exception e) {
            return new Failure(e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
