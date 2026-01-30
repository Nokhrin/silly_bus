package account.operation;

import java.io.PrintStream;

/**
 * Исполняет операцию, формирует вывод.
 */
public class OperationExecutor {
    public static void executeOperation(
            Operation operation,
            PrintStream out,
            PrintStream err
            ) {

        OperationResult operationResult = operation.execute();

    }
}
