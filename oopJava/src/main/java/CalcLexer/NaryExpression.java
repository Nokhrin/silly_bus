package CalcLexer;

import java.util.Optional;
import java.util.Spliterator;

import static CalcLexer.Parsers.*;

public class NaryExpression {
    /**
     * Парсер многоместных операций
     * <p>
     * Многоместная операция - n-арная операция - принимает 2 и более аргументов, возвращает результат
     * Представляется как цепочка бинарных операций с одинаковым приоритетом и ассоциативностью
     * - приоритет - precedence - порядок выполнения операций - например, выполнить * раньше +
     * - ассоциативность - порядок группировки операций с одинаковым приоритетом
     * - левоассоциативный порядок - группировка слева направо - 1 + 2 + 3 + 4 -> (1 + 2) + 3 + 4 -> ((1 + 2) + 3) + 4 и т.д.
     * - правоассоциативный порядок - группировка справа налево - оператор `=` в Java - a = b = c = 0 -> a = b = (c = 0) -> a = (b = (c = 0)) и т.д.
     * <p>
     * Требования:
     * Входной аргумент: строка, представляющая < целое число (0 или более пробелов) оператор (0 или более пробелов) целое число { (0 или более пробелов) целое число } >
     * Правоассоциативный порядок: 1 + 2 + 3 + 4 -> 1 + 2 + (3 + 4) -> 1 + (2 + (3 + 4)) -> (1 + (2 + (3 + 4)))
     * Операторы: +, -, *, /
     * Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     * Знак - отрицательному числу предшествует `-`
     * Пробелы - только между частями - в начале и конце строки не допускаются
     * <p>
     * Задача:
     * - парсить любое количество операций
     * - использовать правоассоциативность
     * - использовать рекурсию
     * <p>
     * <p>
     * EBNF-грамматика многоместной операции
     *  nary_expression ::= num_value {ws} op {ws} nary_expression | num_value
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     */

    /**
     * Композиционный метод, реализующий правило
     * nary_expression ::= num_value {ws} op {ws} nary_expression | num_value
     * <p>
     * декомпозированное на 2 выражения
     * nary_expression ::= expression_1 | expression_2
     * expression_1 :== num_value {ws} op {ws} nary_expression
     * expression_2 :== num_value
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // num_value {ws} op {ws} nary_expression
        Optional<ParseResult<Expression>> expr1 = parseExpression1(source, start);
        if (expr1.isPresent()) { return expr1; }

        // num_value
        Optional<ParseResult<Expression>> expr2 = parseExpression2(source, start);
        if (expr2.isPresent()) { return expr2; }

        return Optional.empty();
    }

    /**
     * expression_1 :== num_value {ws} op {ws} nary_expression
     */
    public static Optional<ParseResult<Expression>> parseExpression1(String source, int start) {
        // num
        int offset = start;
        var num = parseNumber(source, offset);
        if (num.isEmpty()) { return Optional.empty(); }
        
        // ws
        var ws1 = parseWhitespace(source, num.get().end());
        
        // op
        var op = parseOperation(source, ws1.map(ws -> ws.end()).orElse(num.get().end()));
        if (op.isEmpty()) { return Optional.empty(); }
        
        // ws2
        var ws2 = parseWhitespace(source, op.get().end());
        
        // expr
        var expr = parseNaryExpression(source, ws2.map(ws -> ws.end()).orElse(op.get().end()));
        if (expr.isEmpty()) { return Optional.empty(); }
        
        return Optional.of(
                new ParseResult(
                        new BinaryExpression(num.get().value(), op.get().value(), expr.get().value()), 
                        start, 
                        expr.get().end()
                )
        );
    }

    /**
     * expression_2 :== num
     */
    public static Optional<ParseResult<Expression>> parseExpression2(String source, int start) {
        // num
        int offset = start;
        var num = parseNumber(source, offset);
        if (num.isEmpty()) { return Optional.empty(); }

        return num.map(v -> new ParseResult<>((Expression) v.value(), v.start(), v.end()));
    }


    
    public static void main(String[] args) {
        System.out.println(parseNaryExpression("1", 0));
        //Optional[ParseResult[value=NumValue[value=1.0], start=0, end=1]]
        System.out.println(parseNaryExpression("1 + 2", 0));
        //Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=5]]
        System.out.println(parseNaryExpression("1 + 2 + 3", 0));
        //Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=BinaryExpression[left=NumValue[value=2.0], op=ADD, right=NumValue[value=3.0]]], start=0, end=9]]
        System.out.println(parseNaryExpression("1 + 2 + 3 + 4", 0));
        //Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=BinaryExpression[left=NumValue[value=2.0], op=ADD, right=BinaryExpression[left=NumValue[value=3.0], op=ADD, right=NumValue[value=4.0]]]], start=0, end=13]]
        //
    }

}
