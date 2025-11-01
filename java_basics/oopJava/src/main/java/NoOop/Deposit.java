package NoOop;

import java.math.BigDecimal;

public class Deposit {
    private BigDecimal amount;

    public Deposit(BigDecimal amount) {
        this.amount = amount;
    }

    public void depositAccount(Account account) {
        account.deposit(this.amount);
        System.out.println("Пополнил счет " + account + " на " + this.amount + " единиц");
    }
}

