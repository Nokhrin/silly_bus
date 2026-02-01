package account.operations;

import account.operations.result.FailureResult;
import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
import account.system.RepositoryResult;
import command.dto.OpenAccountData;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Открыть счет.
 * 
 * SPE
 *  Что делает:
 *  открывает счет
 *  Причины для изменения:
 *  логика открытия счета
 *  Другие обязанности:
 *  нет
 * 
 * Инкапсуляция:
 *  внутреннее состояние скрыто
 *  публичных полей нет
 *  иммутабелен, т.к. record
 *  
 * Безопасность типов
 *  полная типобезопасность - т.к sealed interface
 *  
 */
public record OpenAccount(OpenAccountData d, AccountRepository accountRepository) implements Operation {

    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();
        try {
            // создать account
            Account account = new Account();
            // записать в хранилище
            RepositoryResult<Account> repositoryResult = accountRepository.saveAccount(account);

            // вернуть id счета
            return new SuccessResult<>(
                    account.id(),
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Успешно открыт счет " + account.id(),
                    repositoryResult.isStaisStateModified()
            );
        } catch (Exception e) {
            return new FailureResult(
                    this.getClass().getSimpleName(),
                    operationId,
                    operationTimestamp,
                    "Ошибка при открытии счета",
                    false
            );

        }
        
    }
}
