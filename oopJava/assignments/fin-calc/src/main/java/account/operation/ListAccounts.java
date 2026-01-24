package account.operation;

import account.system.AccountService; /**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.printf("""
                Существуют открытые счета ...
                %n""");
    }
    // accountService.listAccounts
}
