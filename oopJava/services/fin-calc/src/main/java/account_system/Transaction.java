package account_system;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * • Перевод - с одного счета, другому
 * ◦ Равнозначно операциям снятия и пополнени
 *
 * Представляет финансовую транзакцию с уникальным идентификатором, суммой в мелких единицах валюты и кодом валюты.
 */
public class Transaction {
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


    public static void main(String[] args) {
        /**
         * 3. Создайте 3 объекта `FinancialOperationsDraft.Transaction`:
         *    - Один валидный (например, `id="TX1", amount=100, currency="USD"`),
         *    - Два невалидных (нарушение `amount` и `currency`),
         *    - Убедитесь, что невалидные объекты не создаются.
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