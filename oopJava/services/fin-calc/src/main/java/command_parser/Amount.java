package command_parser;

import java.math.BigDecimal; /**
 * Сумма
 * @param value
 */
public record Amount(BigDecimal value) implements Expression {
    @Override
    public BigDecimal perform() {
        return value;
    }

    @Override
    public String toString() {
        return "Сумма{" + value.toPlainString() + "}";
    }
}
