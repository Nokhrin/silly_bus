package account.operation;

import account.system.Account;
import account.system.AccountService;
import account.system.Amount;

/**
 * Команда.
 */
sealed public interface Operation permits Balance, CloseAccount, Deposit, ListAccounts, OpenAccount, Transfer, Withdraw {
    /**
     * Выполняет команду.
     */
    void execute(AccountService accountService);
}

/**
 * Открыть счет.
 * Возвращает id счета
 */
record OpenAccount() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Открытие счета");
        // accountService.openAccount
        System.out.println("Открыт счет id=" + 1);
    }
    
}

/**
 * Закрыть счет.
 * @param account идентификатор счета
 */
record CloseAccount(Account account) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Закрытие счета id=" + account);
        // accountService.closeAccount
    }
}

/**
 * Вывести список всех открытых счетов.
 */
record ListAccounts() implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.printf("""
                Существуют открытые счета ...
                %n""");
    }
    // accountService.listAccounts
}

/**
 * Зачислить на счет.
 * @param account идентификатор счета
 * @param amount    сумма зачисления
 */
record Deposit(Account account, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Зачисление суммы " + amount + " на счет " + account);
        // accountService.deposit
    }
}

/**
 * Снять со счета.
 * @param account идентификатор счета
 * @param amount    сумма снятия
 */
record Withdraw(Account account, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Снятие суммы " + amount + " со счета " + account);
        // accountService.withdraw
    }
}

/**
 * Перевести со счета на счет.
 * @param sourceAccount идентификатор счета отправителя
 * @param targetAccount идентификатор счета получателя
 * @param amount   сумма перевода
 */
record Transfer(Account sourceAccount, Account targetAccount, Amount amount) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Перевод суммы " + amount + " со счета " + sourceAccount + " на счет " + targetAccount);
        // accountService.transfer
    }
}

/**
 * Посмотреть баланс счета.
 * @param account идентификатор счета
 */
record Balance(Account account) implements Operation {
    @Override
    public void execute(AccountService accountService) {
        System.out.println("Баланс счета " + account);
        // accountService.balance
    }
}
