package account.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Реестр счетов.
 */
public class AccountRegistry {
    private final Map<UUID, BigDecimal> accounts = new HashMap<>();

    /**
     * Открыть счет.
     * - добавить в ассоциативный массив ключ: `id счета`-> значение: `сумма=0`
     *
     * @param accountId - идентификатор счета
     * @return true - счет создан, false - счет не создан, так как уже существует
     */
    public boolean openAccount(UUID accountId) {
        if (accounts.containsKey(accountId)) {
            return false;
        }
        accounts.put(accountId, BigDecimal.ZERO);
        System.out.println("Успешно выполнено открытие счета " + accountId);
        return true;
    }

    /**
     * Закрыть счет.
     * - удалить из ассоциативного массива ключ: `id счета`
     *
     * @param accountId - идентификатор счета
     * @return true - счет успешно удален, false - счет не удален, так как не существует в ассоциативном массиве
     */
    public boolean closeAccount(UUID accountId) {
        if (!accounts.containsKey(accountId)) {
            return false;
        }
        accounts.remove(accountId);
        System.out.println("Успешно выполнено закрытие счета " + accountId);
        return true;
    }

    /**
     * Зачислить сумму на счет.
     * - в ассоциативном массиве увеличить значение ключа accountId на величину amount
     *
     * @param accountId - идентификатор счета
     * @param amount    - сумма зачисления
     * @return true - счет существует, false - счет не существует или сумма некорректна
     */
    public boolean deposit(UUID accountId, BigDecimal amount) {
        // действительно ли валидация суммы должна происходить именно в методе зачисления?
        // или это нарушает принцип разделения ответственностей?
        // Может быть, стоит перенести проверку на null и положительность в парсер, 
        // чтобы он возвращал уже валидированные данные?
        // 
        // мое понимание: в парсере выполняется техническая валидация строки,
        //  то есть, проверка соответствия синтаксиса значения ожидаемому типу данных
        //  этой задачей ограничена ответственность парсера
        // метод зачисления отвечает за бизнес-логику
        //  задача - проверить значение на бизнес-корректность, 
        //  то есть, для зачисления - что сумма не null и >0
        //
        // Стоит ли изменить логику валидации так, 
        // чтобы она не зависела от места вызова? 
        // Например, создать отдельный валидатор, который можно использовать в разных частях системы?
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (accounts.containsKey(accountId)) {
            accounts.merge(accountId, amount, BigDecimal::add);
            // принцип работы java.util.Map.merge
            //  - если ключ accountId не существует в ассоциативном массиве, 
            //      выполнить remappingFunction(null, addAmount), записать accountId -> addAmount 
            //  - если ключ accountId существует в ассоциативном массиве, 
            //      выполнить remappingFunction(initAmount, addAmount), записать accountId -> initAmount + addAmount 
            System.out.println("Успешно выполнено зачисление суммы " + amount + " на счет " + accountId);
            return true;
        }
        System.out.println("Счет " + accountId + " не существует");
        return false;
    }

    /**
     * Снять со счета.
     * - в ассоциативном массиве уменьшить значение ключа accountId на величину amount
     *
     * @param accountId - идентификатор счета
     * @param amount    - сумма снятия
     * @return true - счет существует, false - счет не существует | сумма некорректна | недостаточно средств на счете
     */
    public boolean withdraw(UUID accountId, BigDecimal amount) {
        if (accounts.containsKey(accountId)) {
            BigDecimal balance = accounts.get(accountId);
            if (balance.compareTo(amount) < 0) {
                System.out.println("Недостаточно средств на счете " + accountId);
                return false;
            }

            accounts.merge(accountId, amount, BigDecimal::subtract);

            System.out.println("Успешно выполнено снятие суммы " + amount + " со счета " + accountId);
            return true;
        }
        System.out.println("Счет " + accountId + " не существует");
        return false;
    }

    /**
     * Перевести сумму со счета на счет.
     * - вычесть сумму из значения ключа_1, добавить сумму к значению ключа_2
     *
     * @param sourceAccountId - идентификатор счета снятия
     * @param targetAccountId - идентификатор счета зачисления
     * @param amount          - сумма перевода
     * @return true - счет существует, false - счет не существует | сумма некорректна | недостаточно средств на счете
     */
    public boolean transfer(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount) {
        if (!accounts.containsKey(sourceAccountId)) {
            System.out.println("Счет отправителя " + sourceAccountId + " не существует");
            return false;
        }
        if (!accounts.containsKey(targetAccountId)) {
            System.out.println("Счет получателя " + targetAccountId + " не существует");
            return false;
        }
        if (accounts.get(sourceAccountId).compareTo(amount) < 0) {
            System.out.println("Недостаточно средств на счете " + sourceAccountId);
            return false;
        }
        // валидация значений реализована в методах withdraw и deposit
        // следует ли выполнять валидацию в методе transfer при условии использования withdraw и deposit?
        withdraw(sourceAccountId, amount);
        deposit(targetAccountId, amount);
        return true;
    }

    /**
     * Получить баланс счета.
     */
    public BigDecimal getBalance(UUID accountId) {
        return accounts.get(accountId);
    }

    /**
     * Получить список существующих счетов.
     */
    // следует использовать Optional?
    public String listAccounts() {
        if (accounts.isEmpty()) {
            return "В системе нет счетов";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("В системе существуют счета:\n");
        accounts.forEach(
                (accountId, balance) -> sb.append(" ").
                        append(accountId).append(" -> ").append(balance).append("\n")
        );
        return sb.toString();
    }
}
