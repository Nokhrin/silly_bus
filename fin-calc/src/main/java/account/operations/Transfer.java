package account.operations;

import account.operations.amount.TransactionAmount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
import command.dto.TransferData;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Перевести со счета на счет.
 */
public record Transfer(TransferData transferData, AccountRepository accountRepository) implements Operation {
    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();

        try {
            RepositoryResult<Account> repositoryResultSourceAccount = accountRepository.loadAccount(transferData.getSourceAccountId());
            Account sourceAccount = repositoryResultSourceAccount.value();
            
            if (sourceAccount == null) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Счет источник не найден",
                        false
                );
            }
            
            RepositoryResult<Account> repositoryResultTargetAccount = accountRepository.loadAccount(transferData.getTargetAccountId());
            Account targetAccount = repositoryResultTargetAccount.value();
            if (targetAccount == null) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Счет назначения не найден",
                        false
                );
            }

            Account sourceAccountAfterWithdraw = sourceAccount.withdraw(new TransactionAmount(transferData.amount()));
            if (sourceAccount.balance().equals(sourceAccountAfterWithdraw.balance())) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Недостаточно средств для перевода",
                        false
                );
            }

            // зачисление
            Account targetAccountAfterDeposit = targetAccount.deposit(new TransactionAmount(transferData.amount()));


            repositoryResultTargetAccount = accountRepository.saveAccount(targetAccountAfterDeposit);
            repositoryResultSourceAccount = accountRepository.saveAccount(sourceAccountAfterWithdraw);

            boolean isStateModified = false;
            if (repositoryResultSourceAccount.isStateModified() || repositoryResultTargetAccount.isStateModified()) {
                isStateModified = true;
            }
            
            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно выполнен перевод со счета на счет",
                    isStateModified
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при перевод со счета на счет: " + e.getMessage(),
                    false
            );

        }

    }
}
