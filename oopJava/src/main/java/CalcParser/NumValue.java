package CalcParser;

/**
 * Числовой результат вычисления
 *
 * @param value 
 */
public record NumValue(double value) implements Expression {
    @Override
    public double evaluate() {
        return value;
    }
}
