package account.operation;

import account.system.AccountService;

import java.util.UUID; /**
 * Закрыть счет.
 * @param accountId идентификатор счета
 */
public record CloseAccount(UUID accountId) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Закрытие счета id=" + accountId);
        // accountService.closeAccount
    }
}
