package CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

public class BinOpParser {
    /** 
     * Парсер многоместных операций
     * 
     * Многоместная операция - n-арная операция - принимает 2 и более аргументов, возвращает результат
     * Представляется как цепочка бинарных операций с одинаковым приоритетом и ассоциативностью
     *  - приоритет - precedence - порядок выполнения операций - например, выполнить * раньше +
     *  - ассоциативность - порядок группировки операций с одинаковым приоритетом
     *   - левоассоциативный порядок - группировка слева направо - 1 + 2 + 3 + 4 -> (1 + 2) + 3 + 4 -> ((1 + 2) + 3) + 4 и т.д.
     *   - правоассоциативный порядок - группировка справа налево - оператор `=` в Java - a = b = c = 0 -> a = b = (c = 0) -> a = (b = (c = 0)) и т.д. 
     * 
     * Требования: 
     *  Входной аргумент: строка, представляющая < целое число (0 или более пробелов) оператор (0 или более пробелов) целое число { (0 или более пробелов) целое число } >
     *  Правоассоциативный порядок: 1 + 2 + 3 + 4 -> 1 + 2 + (3 + 4) -> 1 + (2 + (3 + 4)) -> (1 + (2 + (3 + 4)))
     *  Операторы: +, -, *, /
     *  Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     *  Знак - отрицательному числу предшествует `-`
     *  Пробелы - только между частями - в начале и конце строки не допускаются
     *
     * Задача:
     *  - парсить любое количество операций
     *   @todo вопрос: ограничение - максимальный размер стека?
     *  - использовать правоассоциативность
     *  - использовать рекурсию
     *
     * EBNF-грамматика бинарной операции
     *  expr ::= term {ws} op {ws} expr | term
     *  term ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     * 
     * Пояснение к expr
     * Правило expr ::= term {ws} op {ws} expr | term определяет 2 возможных значения expr
     * expr ::= term {ws} op {ws} expr - правоассоциативное правило, где expr - рекурсивное выражение
     * expr ::= term - база рекурсии
     * 
     * @param source - строковое представление вычисляемого выражения 
     * @param start - индекс элемента начала парсинга
     * @return BinOp
     */
    public static Optional<ParseResult<Expression>> parseBinOpChain(String source, int start) {
        // проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;

        //region 1е число
        Optional<ParseResult<Integer>> firstOperand = parseInt(Optional.of(source), offset);
        if (firstOperand.isEmpty()) { return Optional.empty(); }
        offset = firstOperand.get().end();

        // выход из рекурсии
        if (offset >= source.length()) {
            ParseResult<Expression> firstExpr = new ParseResult<>(
                    new NumValue(firstOperand.get().value()),
                    firstOperand.get().start(),
                    firstOperand.get().end()
            );
            return Optional.of(firstExpr);
        }
        
        // преобразование типа firstOperand в Optional<ParseResult<Expression>>
        // не использую Optional, так как при отсутствии оператора выполнение будет прервано ранее
        ParseResult<Expression> firstExpr = new ParseResult<>(
                new NumValue(firstOperand.get().value()),
                firstOperand.get().start(),
                firstOperand.get().end()
        );
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

        //region expr
        // рекурсивно парсим второй операнд, который представляет expr
        Optional<ParseResult<Expression>> expr = parseBinOpChain(source, offset);
        if (expr.isEmpty()) { return Optional.empty() ; }
        offset = expr.get().end();
        //endregion expr
        
        //region создание AST
        Expression groupedExpr = expr.get().value();
        // правоассоциативное преобразование - формируем новый 2й операнд - a op b op c -> a op (b op c)
        BinOpExpression binOp = new BinOpExpression(firstExpr.value(), op.get().value(), groupedExpr);
        //endregion создание AST
        
        return Optional.of(new ParseResult<>(binOp, start, offset));
    }

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

        System.out.println(parseBinOpChain("1", 0));
        //Optional[ParseResult[value=NumValue[value=1.0], start=0, end=1]]
        System.out.println(parseBinOpChain("1 + 2", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=5]]
        System.out.println(parseBinOpChain("1 + 2 + 3", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=BinOpExpression[left=NumValue[value=2.0], op=ADD, right=NumValue[value=3.0]]], start=0, end=9]]
        System.out.println(parseBinOpChain("1 + 2 + 3 + 4", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=BinOpExpression[left=NumValue[value=2.0], op=ADD, right=BinOpExpression[left=NumValue[value=3.0], op=ADD, right=NumValue[value=4.0]]]], start=0, end=13]]
        //
    }

}
