package CalcParser;

/**
 * Выражение
 */
public sealed interface Expression permits NumValue, BinOp, BinaryExpression {
    double evaluate();  // абстрактный метод => наследники обязаны реализовать evaluate
}

/**
 * Выполнение операции (op) над числами left, right
 */
record BinOp(Number left, Parser.Operation op, Number right) implements Expression {
    @Override
    public double evaluate() {
        double leftOperand = left.doubleValue();
        double rightOperand = right.doubleValue();

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
