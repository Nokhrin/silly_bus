package account.operations;

import account.operations.result.OperationResult;

/**
 * Операция.
 */
sealed public interface Operation permits Balance, CloseAccount, Deposit, ListAccounts, OpenAccount, Transfer, Withdraw {
    /**
     * Выполняет операцию.
     * @return Результат операции
     */
    OperationResult execute();
}

