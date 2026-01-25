package account.operation;

import account.system.AccountService;
import account.system.Amount;

import java.util.UUID;

/**
 * Снять со счета.
 *
 * @param accountId идентификатор счета
 * @param amount    сумма снятия
 */
public record Withdraw(UUID accountId, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        accountService.withdraw(accountId, amount);
        System.out.println("Снятие суммы " + amount + " со счета " + accountId);
    }
}
