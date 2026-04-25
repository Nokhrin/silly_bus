package account.operations;

import account.operations.result.OperationResult;

/**
 * Исполняет операцию.
 */
public class OperationExecutor {
    public OperationResult execute(Operation operation) {
        return operation.execute();
    }
}