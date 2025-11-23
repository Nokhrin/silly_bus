package DemoTyping;

import java.math.BigDecimal;

public class Withdrawal implements OperationInterface {
    // в Java класс не обязан использовать все поля, объявленные в интерфейсе
    // класс `FinancialOperationsDraft.Withdrawal` не реализует поле `optionalMessage`
    BigDecimal amount = BigDecimal.valueOf(100);
    public void perform() {
        System.out.println("выполнить снятие " + amount + " единиц");

        // поле `optionalMessage` не реализовано в классе
        // поле `optionalMessage` является константой интерфейса, то есть конкретным - не абстрактным объектом -
        // и может быть использовано без реализации
        if (optionalMessage == null) {
            System.out.println("сообщение не передано");
        }
    }
}
