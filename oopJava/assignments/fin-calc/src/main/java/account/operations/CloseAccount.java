package account.operations;

import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import command.dto.CloseAccountData;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Закрыть счет.
 */
public record CloseAccount(CloseAccountData closeAccountData, AccountRepository accountRepository) implements Operation {
    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();
        try {
            accountRepository.deleteAccount(closeAccountData.getAccountId());

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно закрыт счет " + closeAccountData.getAccountId(),
                    true
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при закрытии счета",
                    false
            );

        }

    }
}
