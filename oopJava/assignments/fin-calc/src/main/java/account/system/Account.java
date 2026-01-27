package account.system;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * Счет.
 */
public class Account {
    private final UUID id;
    private BigDecimal balance = new BigDecimal(BigInteger.ZERO);

    /**
     * Конструктор по умолчанию.
     * Случайный id при открытии счета
     */
    public Account() {
        this.id = UUID.randomUUID();
    }

    /**
     * Конструктор по требованию.
     * Известный id при открытии счета
     * Для тестов
     */
    public Account(UUID id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount) {
        this.balance = this.balance.add(amount.getValue());
    }

    public void withdraw(Amount amount) {
        this.balance = this.balance.subtract(amount.getValue());
    }

    public UUID getId() {
        return this.id;
    }
}
