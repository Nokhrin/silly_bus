package account.operations;

import account.operations.amount.Amount;
import account.operations.amount.PositiveAmount;
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
            Account sourceAccountAfterWithdraw = sourceAccount.withdraw(new PositiveAmount(transferData.amount()));
            repositoryResultSourceAccount = accountRepository.saveAccount(sourceAccountAfterWithdraw);
            
            RepositoryResult<Account> repositoryResultTargetAccount = accountRepository.loadAccount(transferData.getTargetAccountId());
            Account targetAccount = repositoryResultTargetAccount.value();
            Account targetAccountAfterDeposit = targetAccount.deposit(new PositiveAmount(transferData.amount()));
            repositoryResultTargetAccount = accountRepository.saveAccount(targetAccountAfterDeposit);

            boolean isStateModified = false;
            if (repositoryResultSourceAccount.isStaisStateModified() || repositoryResultTargetAccount.isStaisStateModified()) {
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
                    "Ошибка при перевод со счета на счет",
                    false
            );

        }

    }
}
