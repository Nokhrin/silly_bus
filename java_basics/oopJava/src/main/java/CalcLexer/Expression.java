package CalcLexer;

/**
 * Добавить sealed интерфейс Expression
 *
 * sealed interface Expression {
 * double evaluate() // вычисление
 * }
 *
 * record NumValue( double value ) implements Expression {} // тут вычисление - это возврат value
 *
 * record BinOp( Number left, Op op, Number right ) implements Expression {}
 * // тут вычисление - это выполнение операции  (op) над операндами left, right
 *
 * написание тестов
 * 
 * 
 * понимание
 * sealed interface - java 17+ - инструмент ограничения наследования интерфейсов
 * sealed interface указываются те и только те классы, которые могут наследовать объявляемый интерфейс
 * ограничивает область проверки для компилятора - компилятор может ограничить область допустимых наследников по сигнатуре sealed interface
 *  - явно прослеживается на операторе switch - проверке всех возможных сопоставлений за счет их явно декларированного множества
 * как средство проектирования - блокирует непредусмотренное наследование
 */
sealed interface Expression permits NumValue, BinOp {
    double evaluate();  // абстрактный метод => наследники обязаны реализовать evaluate
}

/**
 * Результат вычисления
 * @param value
 */
record NumValue(double value) implements Expression {
    @Override
    public double evaluate() { return value; }
}

/**
 * Выполнение операции (op) над операндами left, right
 * 
 * Операнд - аргумент, участвующий в унарной или бинарной операции
 */
record BinOp(
        Number left,
        Parsers.Operation op,
        Number right
) implements Expression {
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
                if (Math.abs(rightOperand) <= 0) { throw new ArithmeticException("Деление на ноль"); }
                yield leftOperand / rightOperand;
            }
        };
    }
}
