package account.system;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * Счет.
 */
public class Account {
    private final UUID id = UUID.randomUUID();
    private BigDecimal balance = new BigDecimal(BigInteger.ZERO);

    public Account() {
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
