package account.operations.result;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Результат операции.
 */
public sealed interface OperationResult permits SuccessResult, FailureResult {
    String operationName();
    UUID operationId();
    LocalDateTime operationTimestamp();
    String message();
    boolean isSuccess();

    /**
     * Получает значение результата при успехе.
     */
    default Object getValue() {
        if (this instanceof SuccessResult success) {
            return success.value();
        }
        throw new IllegalArgumentException("Неудача операции, значения нет");
    }

    /**
     * Получает описание ошибки при неудаче.
     */
    default String getException() {
    if (this instanceof FailureResult failure) {
        return failure.message();
    }
        throw new IllegalArgumentException("Удачная операция, исключения нет");
    
}


}