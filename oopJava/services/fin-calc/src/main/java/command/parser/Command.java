package command.parser;

/**
 * Команда.
 * Выполняется.
 *
 * <p>
 * Требования:
 * <ul>
 *   <li>Команды реализуют интерфейс {@link Command}.</li>
 *   <li>Команда — это неизменяемый record (или класс), содержащий данные.</li>
 *   <li>Команды неизменяемы (immutable).</li>
 * </ul>
 *
 * <p>
 * Команды — это инструкции, которые знают, какие действия нужно выполнить.
 * Реализация действий делегирована системе управления счетами.
 *
 * <p>
 * Поддерживаемые команды:
 * <ul>
 *   <li><code>open</code> — создать новый счёт (возвращает номер).</li>
 *   <li><code>close &lt;account_id&gt;</code> — закрыть счёт.</li>
 *   <li><code>deposit &lt;account_id&gt; &lt;amount&gt;</code> — пополнить счёт.</li>
 *   <li><code>withdraw &lt;account_id&gt; &lt;amount&gt;</code> — снять со счёта.</li>
 *   <li><code>transfer &lt;source_id&gt; &lt;target_id&gt; &lt;amount&gt;</code> — перевести со счёта на счёт.</li>
 *   <li><code>balance &lt;account_id&gt;</code> — посмотреть баланс.</li>
 *   <li><code>list</code> — вывести список всех открытых счётов.</li>
 * </ul>
 *
 * @since 1.0
 */
sealed interface Command permits Balance, Close, Deposit, List, Open, Transfer, Withdraw {
    /**
     * Выполняет команду.
     */
    void execute();
}

/**
 * Создать новый счёт (возвращает номер).
 * <p>
 * Используется в команде <code>open</code>.
 */
record Open() implements Command {
    @Override
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
record Close(String accountId) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Закрытие счёта:
                закрываемый счёт=%s
                %n""", accountId);
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
record Deposit(String accountId, java.math.BigDecimal amount) implements Command {
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
record Withdraw(String accountId, java.math.BigDecimal amount) implements Command {
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
record Transfer(String sourceId, String targetId, java.math.BigDecimal amount) implements Command {
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
record Balance(String accountId) implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Баланс
                  счёт=%s
                , сумма=%s
                %n""", accountId, getBalance(accountId));
    }

    // Вспомогательный метод (на практике — из account_system)
    private java.math.BigDecimal getBalance(String accountId) {
        return java.math.BigDecimal.ZERO; // эмуляция
    }
}

/**
 * Вывести список всех открытых счётов.
 * <p>
 * Требуется доступ к реестру счётов.
 *
 * @since 1.0
 */
record List() implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Существуют открытые счёта ...
                %n""");
    }
}