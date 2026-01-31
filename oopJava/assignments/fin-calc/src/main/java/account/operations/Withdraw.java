package account.operations;

import account.operations.amount.Amount;
import account.operations.amount.PositiveAmount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
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
            Account account = accountRepository.loadAccount(withdrawData.accountId());
            Account accountAfterWithdraw = account.withdraw(new PositiveAmount(withdrawData.amount()));
            accountRepository.saveAccount(accountAfterWithdraw);

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно снято " + withdrawData.amount() + " со счета " + account.id(),
                    true
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при снятии",
                    false
            );

        }

    }
}
