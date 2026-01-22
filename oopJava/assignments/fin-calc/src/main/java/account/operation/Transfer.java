package account.operation;

import account.system.Account;

import java.math.BigDecimal;

/**
 * Класс для перевода средств между счетами.
 */
public class Transfer extends Operation {
    /**
     * Сумма перевода.
     */
    private BigDecimal amount;

    /**
     * Счет-отправитель.
     */
    private Account source;

    /**
     * Счет-получатель.
     */
    private Account target;

    @Override
    public void perform() {
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
        System.out.println();
        System.out.flush();
    }
}