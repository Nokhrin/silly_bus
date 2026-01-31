package account.operations;

import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import command.dto.ListAccountsData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Вывести список всех открытых счетов.
 */
public record ListAccounts(ListAccountsData listAccountsData, AccountRepository accountRepository) implements Operation {


    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();
        try {
            // прочитать хранилище
            List<Account> accountList = accountRepository.loadExistingAccounts();
            String accountListStr = accountList.stream()
                    .map(Account::id)
                    .map(UUID::toString)
                    .collect(Collectors.joining(" "));

            // вернуть id счета
            return new SuccessResult<>(
                    accountListStr,
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "В системе существуют счета: " + accountListStr,
                    false
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка чтения счетов",
                    false
            );

        }

    }
}
