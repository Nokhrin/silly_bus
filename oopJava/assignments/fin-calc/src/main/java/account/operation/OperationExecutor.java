package account.operation;

import account.system.AccountService;

/**
 * Выполняет операцию, обрабатывает результат.
 */
public class OperationExecutor {
    public static void executeOperation(Operation operation, AccountService accountService) {
        OperationResult operationResult = operation.execute(accountService);
        
        switch (operationResult) {
            // логирование
            case Success s -> {
                s.account().ifPresent(account -> {
                    System.out.println("Успех операции " + operation.toString() + " по счету " + account.getId());
                });
            }
            case Failure f -> {
                System.err.println("Успех операции " + operation.toString() + "\n" + f.message());
            }
        }
    }
}
