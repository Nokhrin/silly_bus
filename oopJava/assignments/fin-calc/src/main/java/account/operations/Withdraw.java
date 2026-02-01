package account.operations;

import account.operations.amount.TransactionAmount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
import command.dto.WithdrawData;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Снять со счета.
 */
public record Withdraw(WithdrawData withdrawData, AccountRepository accountRepository) implements Operation {
    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();

        try {
            RepositoryResult<Account> repositoryResult = accountRepository.loadAccount(withdrawData.accountId());
            Account account = repositoryResult.value();

            if (account == null) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Счет не найден",
                        false
                );
            }

            Account accountAfterWithdraw = account.withdraw(new TransactionAmount(withdrawData.amount()));

            if (account.balance().equals(accountAfterWithdraw.balance())) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Недостаточно средств для снятия",
                        false
                );
            }
            
            
            repositoryResult = accountRepository.saveAccount(accountAfterWithdraw);

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно снято " + withdrawData.amount() + " со счета " + account.id(),
                    repositoryResult.isStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при снятии: " + e.getMessage(),
                    false
            );

        }

    }
}
