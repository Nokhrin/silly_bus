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
}

/**
 * Результат вычисления
 * @param value
 */
record NumValue(double value) implements Expression {}

/**
 * Выполнение операции  (op) над операндами left, right
 */
record BinOp(
        Number left,
        Parsers.Operation op,
        Number right
) implements Expression {}
