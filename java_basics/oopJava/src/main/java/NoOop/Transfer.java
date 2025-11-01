package NoOop;

import java.math.BigDecimal;

public class Transfer {
    private BigDecimal amount;
    private Account source;
    private Account target;

    public Transfer(BigDecimal amount, Account source, Account target) {
        this.amount = amount;
        this.source = source;
        this.target = target;
    }

    public void transferAmountFromTo() {
        System.out.printf("""
            Счет-отправитель %s, баланс %.2f RUB
            Счет-получатель %s, баланс %.2f RUB
            Сумма перевода %.2f RUB
            """, source, source.getBalance(), target, target.getBalance(), amount,
                System.lineSeparator());

        this.source.withdraw(this.amount);
        this.target.deposit(this.amount);
        System.out.printf("""
                        Перевел %.2f RUB
                        """, this.amount,
                System.lineSeparator());

        System.out.printf("""
            Счет-отправитель %s, баланс %.2f RUB
            Счет-получатель %s, баланс %.2f RUB
            """, source, source.getBalance(), target, target.getBalance(),
                System.lineSeparator());
        System.out.flush();
    }
}
