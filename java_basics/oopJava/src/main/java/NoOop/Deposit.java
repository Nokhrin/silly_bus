package NoOop;

import java.math.BigDecimal;

public class Deposit {
    private BigDecimal amount;

    public Deposit(BigDecimal amount) {
        this.amount = amount;
    }

    public void addMoneyToAccount(Account account) {
        account.deposit(this.amount);
    }
}

