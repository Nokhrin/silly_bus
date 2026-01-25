package account.operation;

import account.system.AccountService;
import account.system.Amount;

import java.util.UUID;

/**
 * Перевести со счета на счет.
 *
 * @param sourceAccountId идентификатор счета отправителя
 * @param targetAccountId идентификатор счета получателя
 * @param amount          сумма перевода
 */
public record Transfer(UUID sourceAccountId, UUID targetAccountId, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        accountService.transfer(sourceAccountId, targetAccountId, amount);
        System.out.println("Перевод суммы " + amount + " со счета " + sourceAccountId + " на счет " + targetAccountId);
    }
}
