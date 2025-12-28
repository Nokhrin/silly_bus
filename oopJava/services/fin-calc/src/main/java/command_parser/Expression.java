package command_parser;

import java.math.BigDecimal;

/**
 * Операция над банковским счетом
 */
public sealed interface Expression permits Amount, Operation {
    BigDecimal perform();
}

/**
 * Сумма
 * @param value
 */
record Amount(BigDecimal value) implements Expression {
    @Override
    public BigDecimal perform() {
        return value;
    }

    @Override
    public String toString() {
        return "Сумма{" + value.toPlainString() + "}";
    }
}

