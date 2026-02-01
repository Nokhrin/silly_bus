package account.operations.result;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Успех операции.
 * record => иммутабелен
 * @param value
 * @param operationName
 * @param operationId
 * @param operationTimestamp
 * @param message
 * @param isStateModified
 * @param <T>
 */
public record SuccessResult<T>(
        T value,
        String operationName,
        UUID operationId,
        LocalDateTime operationTimestamp,
        String message,
        boolean isStateModified
) implements OperationResult {

    @Override
    public boolean isSuccess() {
        return true;
    }
}
