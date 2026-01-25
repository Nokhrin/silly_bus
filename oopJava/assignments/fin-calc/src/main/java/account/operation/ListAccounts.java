package account.operation;

import account.system.Account;
import account.system.AccountService;

import java.util.List;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        List<Account> listAccounts = accountService.getAllAccounts();
        System.out.println("Существуют открытые счета: ");
        listAccounts.forEach(account -> System.out.print(account.getId() + "\n"));
        System.out.println();
    }
}
