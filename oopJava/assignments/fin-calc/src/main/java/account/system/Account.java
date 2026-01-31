package account.system;

import account.operations.amount.Amount;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Счет.
 */
public record Account(
        UUID id,
        BigDecimal balance
) {
    

    /**
     * Конструктор по умолчанию.
     * Случайный id при открытии счета
     */
    public Account() {
        this(UUID.randomUUID(), BigDecimal.ZERO);
    }

    /**
     * Конструктор по требованию.
     * Известный id при открытии счета
     * Для тестов
     */
    public Account(UUID id) {
        this(id, BigDecimal.ZERO);
    }

    public Account deposit(Amount amount) {
        return new Account(id, balance.add(amount.getValue()));
    }

    public Account withdraw(Amount amount) {
        if (balance.compareTo(amount.getValue()) < 0) {
            return this;
        };
        return new Account(id, balance.subtract(amount.getValue()));
    }

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

}
