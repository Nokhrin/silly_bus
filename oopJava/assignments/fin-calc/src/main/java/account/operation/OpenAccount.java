package account.operation;

import account.system.AccountService; /**
 * Открыть счет.
 * Возвращает id счета
 */
public record OpenAccount() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Открытие счета");
        // accountService.openAccount
        System.out.println("Открыт счет id=" + 1);
    }
    
}
