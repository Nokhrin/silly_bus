package CalcLexer;

/**
 * Интерфейс с ограничением наследования
 * sealed interface - java 17+ - инструмент ограничения наследования интерфейсов
 * sealed interface указываются те и только те классы, которые могут наследовать объявляемый интерфейс
 * ограничивает область проверки для компилятора - компилятор может ограничить область допустимых наследников по сигнатуре sealed interface
 * - явно прослеживается на операторе switch - проверке всех возможных сопоставлений за счет их явно декларированного множества
 * как средство проектирования - блокирует непредусмотренное наследование
 *
 *
 * todo - Вопрос области видимости
 *
 * 1
 * code inspection IDEA 
 *  для выражения `public static Optional<ParseResult<Expression>> parseNaryOperation(String source, int start) {`
 * @see NaryExpressionNoPriority#parseNaryExpression
 *  возвращает предупреждение `Class 'Expression' is exposed outside its defined visibility scope`
 *  гипотеза о причине предупреждения - ложное срабатывание. 
 *      баг в анализаторе IntelliJ IDEA  в части обработки sealed-интерфейсов
 *   ошибочно считает, что sealed interface с package-private реализациями — это ошибка, 
 *   потому что видит "экспорт" Expression за пределы пакета, но не может найти публичных реализаций. 
 *
 *  ответ: потому что не сделал public
 *
 *  сделал, предупреждение исчезло
 *
 *  однако интерфейс объявлен и реализуется в рамках одного пакета
 *  по умолчанию он должен быть package-private 
 *
 * 2
 *  вопрос - доступа package-private недостаточно в этом случае?
 *
 *  гипотеза:
 *
 *  обоснование почему надо public
 *     инвариант: "все реализации должны быть доступны для наследования"
 *     В Java все публичные сущности, которые участвуют в иерархии (особенно абстрактные), должны быть public
 *     Семантическая точность 
 *     public sealed interface Expression - это контракт -
 *     "Я предоставляю интерфейс для вычисления выражений. Любой класс указанный в permits может его реализовать" 
 *     Если Expression не public, то он не может быть контрактом, потому что не может быть использован
 *
 *  но я использую Expression в пределах package, зачем требовать public?
 *
 *  должны ли класс, реализующие Expression, например, NumValue, быть public?
 *    - предполагаю, нет, главное отличие public sealed interface - общее поведение для многих сущностей
 *    + ограничение наследования, но без ограничения по отношению к наследователям
 *
 */
public sealed interface Expression permits NumValue, BinOp, BinaryExpression {
    double evaluate();  // абстрактный метод => наследники обязаны реализовать evaluate
}

/**
 * Результат вычисления
 *
 * @param value
 *
 *
 * todo - вопрос
 * если класс record NumValue(double value) implements Expression 
 * является НЕ public
 * в реализующем классе IDE создает предупреждение
 * Class 'NumValue' is exposed outside its defined visibility scope
 *
 * если в классе-интерфейсе - конкретно CalcLexer.Expression.java - объявлены одновременно 
 * public sealed interface Expression
 * И
 * public record NumValue(double value) implements Expression
 * возникает ошибка компиляции
 *
 * javac src/main/java/CalcLexer/Expression.java
 * src/main/java/CalcLexer/Expression.java:74: error: class NumValue is public, should be declared in a file named NumValue.java
 * public record NumValue(double value) implements Expression {
 *
 * если объявить
 * public record NumValue(double value) implements Expression
 * в отдельном модуле
 * src/main/java/CalcLexer/NumValue.java
 * предупреждение и ошибка устраняются
 * 
 * поясни, пожалуйста, какая логика стоит за этим решением?
 * один класс - один файл?
 * почему >1 не public класса - допустимо?
 * 
 * 
 * следует ли в src/main/java/CalcLexer/Expression.java оставить только объявление public sealed interface Expression ?
 */
//record NumValue(double value) implements Expression {
//    @Override
//    public double evaluate() {
//        return value;
//    }
//}

/**
 * Выполнение операции (op) над операндами left, right
 *  где left, right - числа
 * <p>
 * Операнд - аргумент, участвующий в унарной или бинарной операции
 */
record BinOp(Number left, Parsers.Operation op, Number right) implements Expression {
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
 * Выполнение операции (op) над операндами left, right
 *  где left, right - выражения
 * <p>
 * Операнд - аргумент, участвующий в унарной или бинарной операции
 *
 * todo - вопрос
 * является ли класс record BinaryExpression(Expression left ...
 * преемником класса  (Number left, ...  ?
 *
 * интерпретирую семантику по имени классов record BinaryExpression и record BinOp
 * BinaryExpression кажется избыточным по смыслу, так как включает понятия Операция и Выражение,
 * где операция - подмножество выражения
 *
 * однако BinOp описывает работу с Number, что, семантически, вижу как более точное и строгое описание
 * при этом BinaryExpression - как менее строгое, но более емкое
 * так как могу рассмотреть число как подмножество Выражений/Expressions
 *
 * BinOp вижу как необходимый вспомогательный тип для парсинга числового значения
 *
 * Верно ли , что один из этих классов избыточен?
 * Если оба требуются, то почему?
 * Как корректно отразить семантику в именах классов?
 *
 * Допустимо ли преобразовать BinaryExpression в BinaryExpression ?
 * BinaryExpression - потому что принимает 2 операнда, являющихся Expressions
 *
 * @see BinOp
 */
record BinaryExpression(Expression left, Parsers.Operation op, Expression right) implements Expression {
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
