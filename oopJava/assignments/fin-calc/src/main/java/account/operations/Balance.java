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
            RepositoryResult<Account> repositoryResult = accountRepository.loadAccount(balanceData.getAccountId());
            Account account = repositoryResult.value();
            BigDecimal balance = account.balance();

            // вернуть id счета
            return new SuccessResult<>(
                    balance,
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Баланс счета " + account.id() + ": " + account.balance(),
                    repositoryResult.isStaisStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при чтении баланса счета",
                    false
            );

        }

    }
}
