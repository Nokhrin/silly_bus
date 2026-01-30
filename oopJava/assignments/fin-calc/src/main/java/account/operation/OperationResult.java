package account.operation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Результат операции.
 */
public interface OperationResult {
    /**
     * Имя операции.
     */
    String operationName();

    /**
     * id операции.
     */
    UUID operationId();

    /**
     * Дата-время операции.
     */
    LocalDateTime operationTimestamp();

    /**
     * Пояснение результата операции.
     */
    String message();

    /**
     * Определить успех/неудача выполнения.
     */
    boolean isSuccess();

    /**
     * Получить значение.
     */
    default Optional<Object> value() {
        return Optional.empty();
    }
}


