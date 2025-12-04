package CalcParser;

/**
 * Выражение
 */
public sealed interface Expression permits AtomExpression, BinaryExpression, NumValue {
    double evaluate();  // абстрактный метод => наследники обязаны реализовать evaluate
}

/**
 * Выполнение операции (op) над выражениями left, right
 */
record BinaryExpression(Expression left, Parser.Operation op, Expression right) implements Expression {
    @Override
    public double evaluate() {
        double leftOperand = left.evaluate();
        double rightOperand = right.evaluate();

        // значения для pattern matching строго ограничены определением Operation
        return switch (op) {
            case ADD -> leftOperand + rightOperand;
            case SUB -> leftOperand - rightOperand;
            case MUL -> leftOperand * rightOperand;
            case DIV -> {
                // проверка деления на ноль
                if (rightOperand == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                yield leftOperand / rightOperand;
            }
        };
    }
}

/**
 * Атомарное выражение
 */
record AtomExpression(Expression left, Parser.Operation op, Expression right) implements Expression {
    @Override
    public double evaluate() {
        double leftOperand = left.evaluate();
        double rightOperand = right.evaluate();

        // значения для pattern matching строго ограничены определением Operation
        return switch (op) {
            case ADD -> leftOperand + rightOperand;
            case SUB -> leftOperand - rightOperand;
            case MUL -> leftOperand * rightOperand;
            case DIV -> {
                // проверка деления на ноль
                if (rightOperand == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                yield leftOperand / rightOperand;
            }
        };
    }
}

/**
 * Числовой результат вычисления
 *
 * @param value
 */
record NumValue(double value) implements Expression {
    @Override
    public double evaluate() {
        return value;
    }
}