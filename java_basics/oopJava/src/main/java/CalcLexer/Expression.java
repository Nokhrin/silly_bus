package CalcLexer;

/*
 * 3) Добавить sealed интерфейс Expression
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
*/ 
/** 
 * Интерфейс с ограничением наследования
 * sealed interface - java 17+ - инструмент ограничения наследования интерфейсов
 * sealed interface указываются те и только те классы, которые могут наследовать объявляемый интерфейс
 * ограничивает область проверки для компилятора - компилятор может ограничить область допустимых наследников по сигнатуре sealed interface
 *  - явно прослеживается на операторе switch - проверке всех возможных сопоставлений за счет их явно декларированного множества
 * как средство проектирования - блокирует непредусмотренное наследование
 */
sealed interface Expression permits NumValue, BinOp, BinOpExpression {
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
                if (rightOperand == 0) { throw new ArithmeticException("Деление на ноль"); }
                yield leftOperand / rightOperand;
            }
        };
    }
}


/*
 * 4) изменение BinOp
 *
 * меняем
 * record BinOp( Number left, Op op, Number right ) 
 *
 * на
 * record BinOp( Expression left, Op op, Expression right ) 
 *
 * тесты на структуры BinOp
 * 1
 * 1+2
 * 1+2+3
 * 1+2+3-4
 *
 * пока без функции парсинга
 */

/**
 * Выполнение операции (op) над операндами left, right
 *
 * Операнд - аргумент, участвующий в унарной или бинарной операции
 */
record BinOpExpression(
        Expression left,
        Parsers.Operation op,
        Expression right
) implements Expression {
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
                if (rightOperand == 0) { throw new ArithmeticException("Деление на ноль"); }
                yield leftOperand / rightOperand;
            }
        };
    }
}
