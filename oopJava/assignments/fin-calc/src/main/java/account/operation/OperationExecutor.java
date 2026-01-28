package account.operation;

import account.system.AccountService;

import java.io.PrintStream;

/**
 * Выполняет операцию, обрабатывает результат.
 */
public class OperationExecutor {
    public static void executeOperation(
            Operation operation,
            AccountService accountService,
            PrintStream out,
            PrintStream err
            ) {

        OperationResult operationResult = operation.execute(accountService);

        String output = switch (operationResult) {
            case Success s -> {
                if (s.message().equals("Успешное выполнение")) {
                    yield "ok";
                } else {
                    yield "ok " + s.message();
                }
            }
            case Failure f -> "err " + f.message();
        };
        
        if (operationResult.isSuccess()) {
            out.println(output);
        } else {
            err.println(output);
        }
    }
}
