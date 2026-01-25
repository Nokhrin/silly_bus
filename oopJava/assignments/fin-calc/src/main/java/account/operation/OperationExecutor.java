package account.operation;

import account.system.AccountService;

public class OperationExecutor {
    public static void executeOperation(Operation operation, AccountService accountService) {
        operation.execute(accountService);
    }
}
