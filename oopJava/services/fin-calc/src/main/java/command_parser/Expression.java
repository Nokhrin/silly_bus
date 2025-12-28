package command_parser;

import java.math.BigDecimal;

/**
 * Операция над банковским счетом
 */
public sealed interface Expression permits Amount, Operation {
    BigDecimal perform();
}

/**
 * Операция
 * @param type
 * @param amount
 * @param fromId
 * @param toId
 */
record Operation(String type, BigDecimal amount, Integer fromId, Integer toId) implements Expression {
    @Override
    public BigDecimal perform() {
        // Возвращает сумму, которую нужно зачесть/списать
        // применение: валидация, баланс, логика
        return amount;
    }

    @Override
    public String toString() {
        return "Операция{" +
                "тип='" + type + '\'' +
                ", сумма=" + amount.toPlainString() +
                ", отправитель=" + fromId +
                ", получатель=" + toId +
                '}';
    }
}