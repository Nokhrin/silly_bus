package account.operations;

import account.operations.amount.Amount;
import account.operations.amount.PositiveAmount;
import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import command.dto.DepositData;

import java.math.BigDecimal;
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
            Account account = accountRepository.loadAccount(depositData.getAccountId());
            Account accountAfterDeposit = account.deposit(new PositiveAmount(depositData.amount()));
            accountRepository.saveAccount(accountAfterDeposit);

            return new SuccessResult<>(
                    "",
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно зачислено " + depositData.amount() + " на счет " + account.id(),
                    true
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при зачислении",
                    false
            );

        }

    }
}
