package DemoTyping;

import java.math.BigDecimal;

interface OperationInterface {
    // поля интерфейса в Java по умолчанию - константы - public static final
    //
    // если закодировать так:
//    BigDecimal amount = null;
//    String optionalMessage = null;
//    void perform();
    // полям будут неявно присвоены атрибуты
    public static final BigDecimal amount = null;
    public static final String optionalMessage = null;
    public abstract void perform();
}
