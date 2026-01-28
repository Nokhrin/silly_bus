package account.operation;

import account.system.Account;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Результат операции.
 */
public sealed interface OperationResult permits Success, Failure {
    /**
     * Имя операции.
     */
    String operationName();

    /**
     * id операции.
     */
    UUID operationId();

    /**
     * Дата-время операции.
     */
    LocalDateTime operationDatetime();

    /**
     * Пояснение результата операции.
     */
    String message();

    /**
     * Определить успех/неудача выполнения.
     */
    boolean isSuccess();

    /**
     * Возвращает счет.
     * При успехе операции
     */
    default Optional<Account> account() {
        return switch (this) {
            case Success s -> s.account();
            case Failure f -> Optional.empty();
        };
    }
}

/**
 * Успех.
 */
record Success(
        String message,
        Optional<Account> account, String operationName, UUID operationId, LocalDateTime operationDatetime) implements OperationResult {

    /**
     * Управляемое сообщение.
     * @param message
     * @param account
     * @param operationName
     */
    public Success(String message, Optional<Account> account, String operationName) {
        this(message, account, operationName, UUID.randomUUID(), LocalDateTime.now());
    }

    /**
     * Сообщение по умолчанию.
     * @param account
     * @param operationName
     */
    public Success(Optional<Account> account, String operationName) {
        this("Успешное выполнение", account, operationName, UUID.randomUUID(), LocalDateTime.now());
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String message() {
        return message;
    }
}


/**
 * Неудача.
 */
record Failure(String message, String operationName, UUID operationId, LocalDateTime operationDatetime) implements OperationResult {
    public Failure(String message, String operationName) {
        this(message, operationName, UUID.randomUUID(), LocalDateTime.now());
    }
    
    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String message() {
        return message;
    }
}
