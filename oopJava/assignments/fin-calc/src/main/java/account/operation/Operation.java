package account.operation;

import account.system.AccountService;

/**
 * Операция.
 */
sealed public interface Operation permits Balance, CloseAccount, Deposit, ListAccounts, OpenAccount, Transfer, Withdraw {
    /**
     * Выполняет операцию.
     * @return Результат операции
     */
    OperationResult execute(AccountService accountService);
}

