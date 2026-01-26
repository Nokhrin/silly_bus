package account.operation;

import account.system.Account;
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
            return new Success(Optional.of(accountService.getAccount(accountId)));
        } catch (Exception e) {
            return new Failure(e.getMessage());
        }
    }
}
