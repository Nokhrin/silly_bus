package account.operation;

import account.system.AccountService;

import java.util.UUID; /**
 * Посмотреть баланс счета.
 * @param accountId идентификатор счета
 */
public record Balance(UUID accountId) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Баланс счета " + accountId);
        // accountService.balance
    }
}
