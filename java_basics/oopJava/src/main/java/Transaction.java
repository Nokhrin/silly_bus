import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * ### **1. Классы и объекты: Создание базовой транзакции**
 * **Цель**: Освоить инкапсуляцию и валидацию данных.
 *
 * **Задания**:
 * 1. Реализуйте класс `Transaction` со следующими требованиями:
 *    - Поля: `id` (строка), `amount` (число), `currency` (строка).
 *    - Конструктор должен проверять:
 *      - `amount > 0`,
 *      - `currency` принадлежит списку `["USD", "EUR", "RUB"]`.
 *    - При нарушении условий выбрасывать `IllegalArgumentException` с понятным сообщением.
 *
 *
 * 2. Напишите статический метод `validate(Transaction t)`, возвращающий `true` только для валидных транзакций.
 *
 * 3. Создайте 3 объекта `Transaction`:
 *    - Один валидный (например, `id="TX1", amount=100, currency="USD"`),
 *    - Два невалидных (нарушение `amount` и `currency`),
 *    - Убедитесь, что невалидные объекты не создаются.
 *
 * ```java
 *  public class Transaction {
 *        private ? id;
 *        private ? amount;
 *        private ? currency;
 *
 *        public Transaction(? id, ? amount, ? currency) {
 *          // . тут реализация .
 *          // Валидация: amount > 0, currency в списке ["USD", "EUR", "RUB"]
 *          // ....
 *        }
 *  }
 *
 *  public class TransactionValidator {
 *     public static boolean validate(Transaction t) {
 *          // . тут реализация .
 *     }
 *
 * // Проверка
 * try {
 *     new Transaction("TX1", 100, "USD"); // ?
 *     new Transaction("TX2", -50, "USD"); // ?
 * } catch (IllegalArgumentException e) {
 *     System.out.println(e.getMessage()); // ?
 * }
 *
 * ```
 *
 * **Каверзные вопросы**:
 * - ❓ Как поведет себя программа, если поля класса объявить как `public` вместо `private`?
 * Приведите пример сценария, где это нарушит бизнес-логику.
 *
 * - ❓ Что произойдет, если валидацию перенести из конструктора в метод `validate()`,
 * но не вызывать его при создании объекта?
 * Как это повлияет на целостность данных?
 *
 * **Ссылки**:
 * - [Classes and Objects (Oracle)](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)
 *
 * ---
 */
public class Transaction {
    /**
     *  * 1. Реализуйте класс `Transaction` со следующими требованиями:
     *  *    - Поля: `id` (строка), `amount` (число), `currency` (строка).
     *  *    - Конструктор должен проверять:
     *  *      - `amount > 0`,
     *  *      - `currency` принадлежит списку `["USD", "EUR", "RUB"]`.
     *  *    - При нарушении условий выбрасывать `IllegalArgumentException` с понятным сообщением.
     */
    private final String id;
    private final BigInteger amount; // Младшая единица валюты - minor currency unit - копейки/центы
    private final String currency;
    private static final Set<String> CURRENCIES_AVAILABLE = new HashSet<>(Set.of("USD", "EUR", "RUB"));

    public Transaction(String id, BigInteger amount, String currency) {
        if (id == null) {
            throw new IllegalArgumentException("В качестве значения id получен null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("В качестве значения суммы получен null");
        }
        if (amount.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("В качестве значения суммы получено отрицательное число");
        }
        if (currency == null) {
            throw new IllegalArgumentException("В качестве кода валюты получен null");
        }
        if (!CURRENCIES_AVAILABLE.contains(currency)) {
            throw new IllegalArgumentException(String.format("""
                    В качестве кода валюты получено недопустимое значение
                    %s
                    
                    """, currency));
        }

        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * 2. Напишите статический метод `validate(Transaction t)`, возвращающий `true` только для валидных транзакций.
     */
    public static boolean isValid(Transaction transaction) {
        if (transaction == null) return false;
        if (transaction.getAmount() == null) return false;
        if (transaction.getAmount().compareTo(BigInteger.ZERO) < 0) return false;
        if (transaction.getCurrency() == null) return false;
        if (!CURRENCIES_AVAILABLE.contains(transaction.currency)) return false;

        // проверки пройдены => транзакция валидна
        return true;
    }


    public static void main(String[] args) {
        /**
         * 3. Создайте 3 объекта `Transaction`:
         *    - Один валидный (например, `id="TX1", amount=100, currency="USD"`),
         *    - Два невалидных (нарушение `amount` и `currency`),
         *    - Убедитесь, что невалидные объекты не создаются.
         *
         *
         * // Проверка
         * try {
         *     new Transaction("TX1", 100, "USD"); // ?
         *     new Transaction("TX2", -50, "USD"); // ?
         * } catch (IllegalArgumentException e) {
         *     System.out.println(e.getMessage()); // ?
         * }
         *
         */
        try {
            Transaction tr1 = new Transaction("TX1", BigInteger.valueOf(10000), "USD");
            Transaction tr2 = new Transaction("TX2", BigInteger.valueOf(-5000), "USD");
            Transaction tr3 = new Transaction("TX3", BigInteger.valueOf(0), "XXX");
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e);
            System.out.flush();
        }
    }
}