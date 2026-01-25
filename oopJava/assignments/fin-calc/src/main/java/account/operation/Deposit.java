package account.operation;

import account.system.AccountService;
import account.system.Amount;

import java.util.UUID; /**
 * Зачислить на счет.
 * @param accountId идентификатор счета
 * @param amount    сумма зачисления
 */
public record Deposit(UUID accountId, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Зачисление суммы " + amount.getValue() + " на счет " + accountId);
        // accountService.deposit
    }
}
