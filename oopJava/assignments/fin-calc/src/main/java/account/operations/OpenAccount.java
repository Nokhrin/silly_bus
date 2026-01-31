package account.operations;

import account.operations.result.OperationResult;
import account.operations.result.SuccessResult;
import account.system.Account;
import account.system.AccountRepository;
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
    
//    // репозиторий передан по умолчанию
//    public OpenAccount(OpenAccountData d) {
//        // AccountRepository accountRepository из глобальной области видимости
//    }

    @Override
    public OperationResult execute() {
        UUID operationId = UUID.randomUUID();
        LocalDateTime operationTimestamp = LocalDateTime.now();
        try {
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
        } catch (Exception e) {
            return new SuccessResult<>(
                    "Открытие счета",
                    operationId,
                    operationTimestamp,
                    "Ошибка при открытии счета",
                    null
            );

        }
        
    }
}
