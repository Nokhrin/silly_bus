package CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

public class NaryExpressionLeftRecursion {
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
     * EBNF-грамматика многоместной операции
     *  nary_expression ::= num_value {ws} op {ws} nary_expression | num_value
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     * <p>
     * <p>
     * Задача:
     * - объяснить понятие левой рекурсии
     * <p>
     * <p>
     * Дано правило
     * nary_expression_right ::= num_value {ws} op {ws} nary_expression_right | num_value
     * <p>
     * Правило является рекурсивным, так как описывает вызов самого себя словом nary_expression_right
     * Вызов nary_expression_right расположен в правой части выражение,
     * поэтому выражение называется праворекурсивным
     * Вызов Праворекурсивным выражением самого себя выполняется в конце выражения,
     * поэтому в части выражения, предшествующей рекурсивному вызову можно описать условия прекращения вычисления
     * <p>
     * Дано правило
     * nary_expression_left ::= nary_expression_left {ws} op {ws} num_value | num_value
     * <p>
     * Рекурсивный вызов является первой операцией, ему не предшествуют иные операции,
     * с помощью которых можно описать прекращение вычисления
     * Возникает бесконечный цикл вызовов
     * <p>
     * <p>
     * Левая рекурсия НЕ ЕСТЬ левоассоциативность
     * "Направление" рекурсии не связано с понятием ассоциативности
     * <p>
     * <p>
     * Изменим правило парсинга на леворекурсивное
     *
     * EBNF-грамматика многоместной операции
     *  nary_expression ::= nary_expression {ws} op {ws} num_value | num_value
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     *  
     * Компиляция успешна
     * В runtime получаем переполнение стека
     * Exception in thread "main" java.lang.StackOverflowError
     * 
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // nary_expression {ws} op {ws} num_value
        Optional<ParseResult<Expression>> expr1 = parseExpression1(source, start);
        if (expr1.isPresent()) {
            return expr1;
        }

        // num_value
        Optional<ParseResult<Expression>> expr2 = parseExpression2(source, start);
        if (expr2.isPresent()) {
            return expr2;
        }

        return Optional.empty();
    }

    /**
     * expression_1 :== nary_expression {ws} op {ws} num_value
     */
    public static Optional<ParseResult<Expression>> parseExpression1(String source, int start) {
        int offset = start;

        // expr
        var expr = parseNaryExpression(source, offset);
        if (expr.isEmpty()) {
            return Optional.empty();
        }
        
        // ws
        var ws1 = parseWhitespace(source, expr.get().end());

        // op
        var op = parseOperation(source, ws1.map(ws -> ws.end()).orElse(expr.get().end()));
        if (op.isEmpty()) {
            return Optional.empty();
        }

        // ws2
        var ws2 = parseWhitespace(source, op.get().end());

        // num
        var num = parseNumber(source, ws2.map(ws -> ws.end()).orElse(expr.get().end()));
        if (num.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new ParseResult(
                        new BinaryExpression(expr.get().value(), op.get().value(), num.get().value()),
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
        if (num.isEmpty()) {
            return Optional.empty();
        }

        return num.map(v -> new ParseResult<>((Expression) v.value(), v.start(), v.end()));
    }


    public static void main(String[] args) {
        System.out.println(parseNaryExpression("1 +2  + 3", 0));
        // Exception in thread "main" java.lang.StackOverflowError
        //	at CalcLexer.NaryExpressionLeftRecursion.parseExpression1(NaryExpressionLeftRecursion.java:82)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseNaryExpression(NaryExpressionLeftRecursion.java:64)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseExpression1(NaryExpressionLeftRecursion.java:85)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseNaryExpression(NaryExpressionLeftRecursion.java:64)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseExpression1(NaryExpressionLeftRecursion.java:85)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseNaryExpression(NaryExpressionLeftRecursion.java:64)
        //	at CalcLexer.NaryExpressionLeftRecursion.parseExpression1(NaryExpressionLeftRecursion.java:85)
    }

}
