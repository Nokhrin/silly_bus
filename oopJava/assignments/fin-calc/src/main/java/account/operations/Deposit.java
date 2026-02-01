package account.operations;

import account.operations.amount.TransactionAmount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
import command.dto.DepositData;

import java.time.LocalDateTime;
import java.util.UUID; 
/**
 * Зачислить на счет.
 */
public record Deposit(DepositData depositData, AccountRepository accountRepository) implements Operation {
    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();

        try {
            RepositoryResult<Account> repositoryResultAccount = accountRepository.loadAccount(depositData.accountId());
            Account account = repositoryResultAccount.value();

            if (account == null) {
                return new FailureResult(
                        this.getClass().getSimpleName(),
                        operationId,
                        operationTimestamp,
                        "Счет не найден",
                        false
                );
            }

            Account accountAfterDeposit = account.deposit(new TransactionAmount(depositData.amount()));
            repositoryResultAccount = accountRepository.saveAccount(accountAfterDeposit);

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно зачислено " + depositData.amount() + " на счет " + account.id(),
                    repositoryResultAccount.isStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при зачислении: " + e.getMessage(),
                    false
            );

        }

    }
}
