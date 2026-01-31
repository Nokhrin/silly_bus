package account.operations;

import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import command.dto.OpenAccountData;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Открыть счет.
 */
public record OpenAccount(OpenAccountData d) implements Operation {

    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();

        // создать account
        Account account = new Account();
        // записать в хранилище
        
        // вернуть id счета исполнителю
        
        return new SuccessResult<>(
                "Открытие счета",
                operationId,
                operationTimestamp,
                "Успешно открыт счет " + account.getId(),
                account.getId()
        );
    }
}
