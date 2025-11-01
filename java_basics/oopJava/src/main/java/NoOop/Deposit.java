package NoOop;

import java.math.BigDecimal;

public class Deposit {
    private BigDecimal amount;
    private Account target;

    public Deposit(BigDecimal amount, Account account) {
        this.amount = amount;
        this.target = account;
    }

    public void performDeposit() {
        System.out.printf("""
                        Счет-получатель %s, баланс %.2f RUB
                        """, target, target.getBalance(),
                System.lineSeparator());
        System.out.flush();

        this.target.deposit(this.amount);
        System.out.printf("""
                        Добавил на счет %.2f RUB
                        """, this.amount,
                System.lineSeparator());

        System.out.printf("""
                        Счет-получатель %s, баланс %.2f RUB
                        """, target, target.getBalance(),
                System.lineSeparator());
        System.out.flush();
    }
}

