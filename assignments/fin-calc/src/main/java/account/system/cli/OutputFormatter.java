package account.system.cli;

import account.operations.result.OperationResult;

/**
 * Форматтер результата операции.
 */
public sealed interface OutputFormatter permits ConsoleOutputFormatter {

    /**
     * Форматировать.
     */
    String format(OperationResult operationResult);
}
