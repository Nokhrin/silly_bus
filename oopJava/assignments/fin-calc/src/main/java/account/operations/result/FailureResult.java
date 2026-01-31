package account.operations.result;

import java.time.LocalDateTime;
import java.util.UUID;

public record FailureResult(
        String operationName,
        UUID operationId,
        LocalDateTime operationTimestamp,
        String message
) implements OperationResult {
    @Override
    public boolean isSuccess() {
        return false;
    }
}
