package NoOop;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final UUID id = UUID.randomUUID();
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(BigDecimal amount) {
        this.balance = amount;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

}
