package account.system;

import java.math.BigDecimal;

public class Withdrawal extends Operation {
    private BigDecimal amount;
    private Account source;

    @Override
    public void perform() {
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
        System.out.println();
        System.out.flush();
    }


}
