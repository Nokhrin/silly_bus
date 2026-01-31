package account.operations.result;

import java.time.LocalDateTime;
import java.util.UUID;

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
