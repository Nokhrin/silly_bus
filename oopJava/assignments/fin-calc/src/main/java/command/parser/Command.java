package command.parser;

import java.util.UUID;

/**
 * Команда.
 */
sealed public interface Command permits Balance, CloseAccount, Deposit, ListAccounts, OpenAccount, Transfer, Withdraw {
    /**
     * Выполняет команду.
     */
    void execute();
}

/**
 * Создать новый счёт (возвращает номер).
 */
record OpenAccount() implements Command {
    @Override
    
    // todo - вернуть номер открытого счета
    public void execute() {
        System.out.printf("""
                Открытие счёта:
                 открыт счёт=%s
                %n""");
    }
}

/**
 * Закрыть счёт.
 * <p>
 * Параметры:
 * <ul>
 *   <li><code>accountId</code> — идентификатор счёта для закрытия.</li>
 * </ul>
 *
 * @param accountId идентификатор счёта
 */
record CloseAccount(UUID accountId) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Закрытие счёта:
                закрываемый счёт=%s
                %n""", accountId);
    }
}

/**
 * Вывести список всех открытых счётов.
 * <p>
 * Требуется доступ к реестру счётов.
 *
 * @since 1.0
 */
record ListAccounts() implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Существуют открытые счёта ...
                %n""");
    }
}

/**
 * Пополнить счёт.
 * <p>
 * Параметры:
 * <ul>
 *   <li><code>accountId</code> — идентификатор счёта для зачисления.</li>
 *   <li><code>amount</code> — сумма зачисления.</li>
 * </ul>
 *
 * @param accountId идентификатор счёта
 * @param amount    сумма зачисления
 */
record Deposit(UUID accountId, java.math.BigDecimal amount) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Зачисление:
                  сумма=%s
                , счёт зачисления=%s
                %n""", amount, accountId);
    }
}

/**
 * Снять со счёта.
 * <p>
 * Параметры:
 * <ul>
 *   <li><code>accountId</code> — идентификатор счёта для снятия.</li>
 *   <li><code>amount</code> — сумма снятия.</li>
 * </ul>
 *
 * @param accountId идентификатор счёта
 * @param amount    сумма снятия
 */
record Withdraw(UUID accountId, java.math.BigDecimal amount) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Снятие:
                  сумма=%s
                , счёт снятия=%s
                %n""", amount, accountId);
    }
}

/**
 * Перевести со счёта на счёт.
 * <p>
 * Параметры:
 * <ul>
 *   <li><code>sourceId</code> — идентификатор счёта отправителя.</li>
 *   <li><code>targetId</code> — идентификатор счёта получателя.</li>
 *   <li><code>amount</code> — сумма перевода.</li>
 * </ul>
 *
 * @param sourceId идентификатор счёта отправителя
 * @param targetId идентификатор счёта получателя
 * @param amount   сумма перевода
 */
record Transfer(UUID sourceId, UUID targetId, java.math.BigDecimal amount) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Перевод
                  сумма=%s
                , счёт отправителя=%s
                , счёт получателя=%s
                %n""", amount, sourceId, targetId);
    }
}

/**
 * Посмотреть баланс.
 * <p>
 * Параметры:
 * <ul>
 *   <li><code>accountId</code> — идентификатор счёта для проверки баланса.</li>
 * </ul>
 *
 * @param accountId идентификатор счёта
 */
record Balance(UUID accountId) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Баланс
                  счёт=%s
                , сумма=%s
                %n""", accountId, get(accountId));
    }

    // Вспомогательный метод (на практике — из account_system)
    private java.math.BigDecimal get(UUID accountId) {
        return java.math.BigDecimal.ZERO; // эмуляция
    }
}
