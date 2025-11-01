package NoOop;

import java.math.BigDecimal;

public class Withdraw {
    private BigDecimal amount;
    private Account source;

    public Withdraw(BigDecimal amount, Account account) {
        this.amount = amount;
        this.source = account;
    }

    public void performWithdraw() {
        System.out.printf("""
                        Счет-отправитель %s, баланс %.2f RUB
                        """, source, source.getBalance(),
                System.lineSeparator());
        System.out.flush();

        this.source.withdraw(this.amount);
        System.out.printf("""
                        Снял со счета %.2f RUB
                        """, this.amount,
                System.lineSeparator());

        System.out.printf("""
                        Счет-отправитель %s, баланс %.2f RUB
                        """, source, source.getBalance(),
                System.lineSeparator());
        System.out.flush();
    }
}
