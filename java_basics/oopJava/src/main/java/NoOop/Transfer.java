package NoOop;

import java.math.BigDecimal;

public class Transfer {
    private BigDecimal amount;

    public Transfer(BigDecimal amount) {
        this.amount = amount;
    }

    public void transferAmountFromTo(Account source, Account target) {
        source.withdraw(this.amount);
        target.deposit(this.amount);
        System.out.println("Перевел со счета " + source + " на счет " + target + " " + this.amount + " единиц");
    }
}
