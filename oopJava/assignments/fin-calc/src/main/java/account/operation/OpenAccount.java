package account.operation;

import account.system.AccountService;

import java.util.UUID;

/**
 * Открыть счет.
 * Возвращает id счета
 */
public record OpenAccount() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Открытие счета");
        UUID accountId = accountService.openAccount();
        System.out.println("Открыт счет " + accountId);
    }
    
}
