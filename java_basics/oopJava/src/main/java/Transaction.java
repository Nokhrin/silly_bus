import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

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
 * - ❓ Как поведет себя программа, если поля класса объявить как `public` вместо `private`? Приведите пример сценария, где это нарушит бизнес-логику.
 * - ❓ Что произойдет, если валидацию перенести из конструктора в метод `validate()`, но не вызывать его при создании объекта? Как это повлияет на целостность данных?
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
    private String id;
    private BigInteger amount; // Младшая единица валюты - minor currency unit
    private String currency;

    public Transaction(BigInteger amount, String currency) {
        // валидация amount
        if (amount == null) {
            throw new IllegalArgumentException("В качестве значения суммы получен null");
        }
        if (amount.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("В качестве значения суммы получено отрицательное число");
        }

        // валидация currency
        if (currency == null) {
            throw new IllegalArgumentException("В качестве кода валюты получен null");
        }
        // допустимые коды валют
        Set<String> currenciesAvailable = new HashSet<>(Set.of("USD", "EUR", "RUB"));
        if (!currenciesAvailable.contains(currency)) {
            throw new IllegalArgumentException(String.format("""
                    В качестве кода валюты получено недопустимое значение
                    %s
                    
                    """, currency));
        }

    }
}

