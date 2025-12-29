package account.system;

import java.math.BigDecimal;

/**
 * Зачисление
 */
public class Deposit extends Operation {
    private BigDecimal amount;
    private Account target;

    @Override
    public void perform() {

        System.out.printf("""
                        Счет-получатель %s, баланс %.2f RUB
                        """, target, target.getBalance());
        System.out.flush();

        target.deposit(amount);
        System.out.printf("""
                        Добавил на счет %.2f RUB
                        """, amount);

        System.out.printf("""
                        Счет-получатель %s, баланс %.2f RUB
                        """, target, target.getBalance());
        System.out.println();
        System.out.flush();
    }

}
