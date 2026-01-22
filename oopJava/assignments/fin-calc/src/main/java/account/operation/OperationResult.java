package account.operation;

import account.system.Account;

import java.util.Optional;

/**
 * Результат операции.
 */
public sealed interface OperationResult permits Success, Failure {
    /**
     * Определить успех/неудача выполнения.
     */
    boolean isSuccess();

    /**
     * Возвращает счет.
     * При успехе операции
     */
    default Optional<Account> account() {
        if (this instanceof Success success) {
            return success.account();
        }
        return Optional.empty();
    }
}

/**
 * Успех.
 */
record Success(Optional<Account> account) implements OperationResult {

    @Override
    public boolean isSuccess() {
        return true;
    }
}

/**
 * Неудача.
 */
record Failure(String message) implements OperationResult {
    @Override
    public boolean isSuccess() {
        return false;
    }
}
