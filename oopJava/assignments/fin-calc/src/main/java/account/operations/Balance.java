package account.operations;

import account.operations.amount.Amount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
import command.dto.BalanceData;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Посмотреть баланс счета.
 */
public record Balance(BalanceData balanceData, AccountRepository accountRepository) implements Operation {

    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();

        try {
            // прочитать account
            RepositoryResult<Account> repositoryResult = accountRepository.loadAccount(balanceData.accountId());
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


            BigDecimal balance = account.balance();

            // вернуть id счета
            return new SuccessResult<>(
                    balance,
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Баланс счета " + account.id() + ": " + account.balance(),
                    repositoryResult.isStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при чтении баланса счета: " + e.getMessage(),
                    false
            );

        }

    }
}
