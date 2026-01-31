package account.operations.result;

import java.time.LocalDateTime;
import java.util.UUID;

public record SuccessResult<T>(
        String operationName,
        UUID operationId,
        LocalDateTime operationTimestamp,
        String message,
        T value
) implements OperationResult {

    @Override
    public boolean isSuccess() {
        return true;
    }
}
