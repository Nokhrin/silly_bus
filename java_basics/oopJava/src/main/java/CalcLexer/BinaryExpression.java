package CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

public class BinaryExpression {
    /** 
     * Парсер бинарных операций - упрощенный/неполный
     * 
     * бинарная операция - двуместная операция Операция, выполняемая над двумя аргументами. Например, сложение аргументов
     *
     *  Синтаксис: целое число (0 или более пробелов) оператор (0 или более пробелов) целое число
     *  Операторы: +, -, *, /
     *  Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     *  Знак - отрицательному числу предшествует `-`
     *  Пробелы - только между частями - в начале и конце строки не допускаются
     *
     * EBNF-грамматика бинарной операции
     * binOp ::= num {ws} op {ws} num
     * num ::= [sign] digit {digit}
     * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     * op ::= "+" | "-" | "*" | "/"
     * sign ::= "+" | "-"
     * ws ::= " " | "\t"
     * 
     * @param source - строка для парсинга
     * @param start - индекс элемента начала парсинга
     * @return BinOp op
     */
    public static Optional<ParseResult<Expression>> parseBinOpSimple(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;

        //region 1е число
        Optional<ParseResult<Integer>> numFirst = parseInt(Optional.of(source), offset);
        if (numFirst.isEmpty()) { return Optional.empty(); }
        offset = numFirst.get().end();
        //endregion 1е число
        
        //region пробелы после 1го числа
        Optional<ParseResult<String>> ws1 = parseWhitespace(Optional.of(source), offset);
        // по условию {ws}
        //  => не проверяю ws1.isEmpty(), так как это значение удовлетворяет условию, 
        //  продолжаю выполнение
        if (ws1.isPresent()) {
            offset = ws1.get().end();
        }
        //endregion пробелы после 1го числа

        //region оператор
        Optional<ParseResult<Operation>> op = parseOperation(Optional.of(source), offset);
        if (op.isEmpty()) { return Optional.empty(); }
        offset = op.get().end();
        //endregion оператор
        
        //region пробелы после оператора
        Optional<ParseResult<String>> ws2 = parseWhitespace(Optional.of(source), offset);
        // по условию {ws}
        //  => не проверяю ws2.isEmpty(), так как это значение удовлетворяет условию, 
        //  продолжаю выполнение
        if (ws2.isPresent()) {
            offset = ws2.get().end();
        }
        //endregion пробелы после оператора

        //region 2е число
        Optional<ParseResult<Integer>> numSecond = parseInt(Optional.of(source), offset);
        if (numSecond.isEmpty()) { return Optional.empty() ; }
        int end = numSecond.get().end();
        //endregion 2е число
            
        return Optional.of(new ParseResult<>(
                // BinOp - реализует Expression, поэтому типизация корректна:
                //  метод возвращает Optional<ParseResult<Expression>>
                //  метод возвращает Optional<ParseResult<Expression>>
                new BinOp(numFirst.get().value(), op.get().value(), numSecond.get().value()),
                offset,
                end
        ));
    }

    public static void main(String[] args) {
        System.out.println(parseBinOpSimple("1 * 2", 0));
        //Optional[ParseResult[value=BinOp[left=1, op=MUL, right=2], start=4, end=5]]
        System.out.println(parseBinOpSimple("1 / 2", 0));
        //Optional[ParseResult[value=BinOp[left=1, op=DIV, right=2], start=4, end=5]]
        System.out.println(parseBinOpSimple("1 + 2", 0));
        //Optional[ParseResult[value=BinOp[left=1, op=ADD, right=2], start=4, end=5]]
        System.out.println(parseBinOpSimple("1 - 2", 0));
        //Optional[ParseResult[value=BinOp[left=1, op=SUB, right=2], start=4, end=5]]
    }

}
