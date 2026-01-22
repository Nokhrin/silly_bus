package account.operation;

import java.math.BigInteger;
import java.util.Set;

/**
 * Представляет финансовую транзакцию с уникальным идентификатором, суммой в мелких единицах валюты и кодом валюты.
 * <p>
 * Транзакция может быть использована для описания:
 * <ul>
 *   <li>перевода со счёта на счёт</li>
 *   <li>пополнения счёта</li>
 *   <li>снятия со счёта</li>
 * </ul>
 * <p>
 * Важно: сумма хранится в мелких единицах (центы, копейки), а не в основных.
 * <p>
 * Допустимые коды валют: "USD", "EUR", "RUB".
 *
 * @since 1.0
 */
public class Transaction {

    /**
     * Уникальный идентификатор транзакции.
     */
    private final String id;

    /**
     * Сумма транзакции в мелких единицах валюты (например, центы для USD, копейки для RUB).
     * Значение всегда неотрицательное.
     */
    private final BigInteger amount;

    /**
     * Код валюты в формате ISO 4217 (например, "USD", "EUR", "RUB").
     * Допустимые значения: "USD", "EUR", "RUB".
     */
    private final String currency;

    /**
     * Доступные коды валют.
     */
    private static final Set<String> CURRENCIES_AVAILABLE = Set.of("USD", "EUR", "RUB");

    /**
     * Создаёт новую транзакцию.
     *
     * @param id       уникальный идентификатор транзакции (не должен быть null)
     * @param amount   сумма в мелких единицах (не должен быть null и должен быть >= 0)
     * @param currency код валюты (не должен быть null, должен быть в списке допустимых)
     * @throws IllegalArgumentException если аргументы недопустимы
     */
    public Transaction(final String id, final BigInteger amount, final String currency) {
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
            throw new IllegalArgumentException(
                    String.format("В качестве кода валюты получено недопустимое значение: %s%n" +
                            "Допустимые значения: %s", currency, CURRENCIES_AVAILABLE));
        }

        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Возвращает уникальный идентификатор транзакции.
     *
     * @return идентификатор транзакции
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает сумму транзакции в мелких единицах валюты.
     *
     * @return сумма в мелких единицах (например, центы)
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     * Возвращает код валюты в формате ISO 4217.
     *
     * @return код валюты (например, "USD")
     */
    public String getCurrency() {
        return currency;
    }
}