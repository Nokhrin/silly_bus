package account.system.cli;

import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;

public final class ConsoleOutputFormatter implements OutputFormatter {

    @Override
    public String format(OperationResult operationResult) {
        return switch (operationResult) {
            case SuccessResult successResult -> formatSuccess(successResult);
            case FailureResult failureResult -> formatFailure(failureResult);
        };
    }

    private String formatFailure(FailureResult failureResult) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("err ");

        // сообщение о выполнении операции
        stringBuilder.append(failureResult.message());

        // сообщение о состоянии системы
        if (failureResult.isStateModified()) {
            stringBuilder.append(" [состояние системы было изменено до ошибки]");
        }

        return stringBuilder.toString();
    }

    private String formatSuccess(SuccessResult successResult) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ok ");

        // сообщение о выполнении операции
        if (successResult.getValue() != null) {
            stringBuilder.append(successResult.message());
        }

        // сообщение о состоянии системы
        if (successResult.isStateModified()) {
            stringBuilder.append(" [состояние системы было изменено]");
        } else {
            stringBuilder.append(" [состояние системы не было изменено]");
        }
        return stringBuilder.toString();
    }
}
