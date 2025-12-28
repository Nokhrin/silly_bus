package account_system;

import java.math.BigDecimal;

public class Transfer extends Operation {
    private BigDecimal amount;
    private Account source;
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
