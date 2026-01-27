package account.operation;

import account.system.AccountService;
import account.system.Amount;

import java.util.Optional;
import java.util.UUID;

/**
 * Снять со счета.
 *
 * @param accountId идентификатор счета
 * @param amount    сумма снятия
 */
public record Withdraw(UUID accountId, Amount amount) implements Operation {
    @Override
    public OperationResult execute(AccountService accountService) {
        try {
            accountService.withdraw(accountId, amount);
            return new Success(Optional.of(accountService.getAccount(accountId)), this.getClass().getSimpleName());
        } catch (Exception e) {
            return new Failure(e.getMessage(), this.getClass().getSimpleName());
        }
    }
}
