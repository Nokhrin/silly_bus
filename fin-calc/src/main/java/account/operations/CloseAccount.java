package account.operations;

import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
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
            RepositoryResult<Account> loadResult = accountRepository.loadAccount(closeAccountData.accountId());
            
            if (loadResult.value() == null || loadResult instanceof RepositoryResult.Failure<Account>) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Счет " + closeAccountData.accountId() + " не существует",
                        false
                );
            }
            
            RepositoryResult<Void> repositoryResult = accountRepository.deleteAccount(closeAccountData.accountId());

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно закрыт счет " + closeAccountData.accountId(),
                    repositoryResult.isStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при закрытии счета: " + e.getMessage(),
                    false
            );

        }

    }
}
