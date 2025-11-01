package NoOop;

import java.math.BigDecimal;

public class Withdraw {
    private BigDecimal amount;

    public Withdraw(BigDecimal amount) {
        this.amount = amount;
    }

    public void withdrawAccount(Account account) {
        account.withdraw(this.amount);
        System.out.println("Снял со счета " + account + " " + this.amount + " единиц");
    }
}
