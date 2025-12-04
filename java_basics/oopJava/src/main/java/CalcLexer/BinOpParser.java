package CalcLexer;

import Calculator.CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

/*
постановка

написать парсер для бинарных операций
упрощенный (не полный)

Бинарная операция
синтаксис

binOp ::= <number> [<ws>] <op> [<ws>] <number>
ws ::= пробел { пробел | табуляция }

результат
record BinOp( Number left, Op op, Number right )

*/


/*
@gochaorg
уточни понятие `упрощенный (не полный) парсер`

гипотеза @Nokhrin

парсер работает только для одного - заданного - шаблона -
в настоящем примере - целое число [пробелы] оператор [пробелы] целое число
  
 */


public class BinOpParser {
    /** 
     * Парсер бинарных операций - упрощенный/неполный
     *
     * 
     * 
     * бинарная операция - двуместная операция Операция, выполняемая над двумя аргументами. Например, сложение аргументов
     * 
     * @todo - уточни
     * Условия задачи: 
     *  Синтаксис: целое число (0 или более пробелов) оператор (0 или более пробелов) целое число
     *  Операторы: +, -, *, /
     * ??? Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     *  Знак - отрицательному числу предшествует `-`
     * ??? Пробелы - только между частями - в начале и конце строки не допускаются ?
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
    
    public static Optional<ParseResult<Expression>> parseBinOpSimple(Optional<String> source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        int offset = start;

        //region 1е число
        Optional<ParseResult<Integer>> numFirst = parseInt(source, offset);
        if (numFirst.isEmpty()) { return Optional.empty(); }
        offset = numFirst.get().end();
        //endregion 1е число
        
        //region пробелы после 1го числа
        Optional<ParseResult<String>> ws1 = parseWhitespace(source, offset);
        // по условию {ws}
        //  => не проверяю ws1.isEmpty(), так как это значение удовлетворяет условию, 
        //  продолжаю выполнение
        offset = ws1.get().end();
        //endregion пробелы после 1го числа

        //region оператор
        Optional<ParseResult<Operation>> op = parseOperation(source, offset);
        if (op.isEmpty()) { return Optional.empty(); }
        offset = op.get().end();
        //endregion оператор
        
        //region пробелы после оператора
        Optional<ParseResult<String>> ws2 = parseWhitespace(source, offset);
        // по условию {ws}
        //  => не проверяю ws2.isEmpty(), так как это значение удовлетворяет условию, 
        //  продолжаю выполнение
        offset = ws2.get().end();
        //endregion пробелы после оператора

        //region 2е число
        Optional<ParseResult<Integer>> numSecond = parseInt(source, offset);
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
        System.out.println(parseBinOpSimple(Optional.of("1 * 2"), 0));
        //Optional[ParseResult[value=BinOp[left=1, op=MUL, right=2], start=4, end=5]]
        
        System.out.println(parseBinOpSimple(Optional.of("1 / 2"), 0));
        //Optional[ParseResult[value=BinOp[left=1, op=DIV, right=2], start=4, end=5]]
        
        System.out.println(parseBinOpSimple(Optional.of("1 + 2"), 0));
        //Optional[ParseResult[value=BinOp[left=1, op=ADD, right=2], start=4, end=5]]
        
        System.out.println(parseBinOpSimple(Optional.of("1 - 2"), 0));
        //Optional[ParseResult[value=BinOp[left=1, op=SUB, right=2], start=4, end=5]]
    }

}
