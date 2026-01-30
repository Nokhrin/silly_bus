package account.operation;

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

