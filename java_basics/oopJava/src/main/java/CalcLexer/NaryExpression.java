package CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

public class NaryExpression {
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
     *  - использовать правоассоциативность
     *  - использовать рекурсию
     *
     * EBNF-грамматика бинарной операции
     *  nary_expression ::= number {ws} op {ws} nary_expression | number
     *  number ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     * 
     * Пояснение к nary_expression
     * Правило nary_expression ::= number {ws} op {ws} nary_expression | number определяет 2 возможных значения nary_expression
     * nary_expression ::= number {ws} op {ws} nary_expression - правоассоциативное правило, где nary_expression - рекурсивное выражение
     * nary_expression ::= number - база рекурсии
     * 
     * todo - вопрос
     * code inspection IDEA 
     *  для выражения `public static Optional<ParseResult<Expression>> parseNaryOperation(String source, int start) {`
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
     *  вопрос - доступа package-private недостаточно в этом случае?
     *  
     *  
     * 
     * @param source - строковое представление вычисляемого выражения 
     * @param start - индекс элемента начала парсинга
     * @return BinOp
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // проверка входных данных
        if (source.isEmpty() || start < 0) { return Optional.empty(); }

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
        ParseResult<Expression> firstExpression = new ParseResult<>(
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

        //region nary_expression
        // рекурсивно парсим второй операнд, который представляет nary_expression
        // пока выражение не завершилось, вызываем parseNaryOperation с актуального смещения
        Optional<ParseResult<Expression>> secondOperand = parseNaryExpression(source, offset);
        // база рекурсии - смысл: дальнейший парсинг не имеет смысла, термов/валидных операндов больше нет
        // parseNaryOperation не должен вызываться для последнего операнда
        if (secondOperand.isEmpty()) { return Optional.empty() ; }
        
        ParseResult<Expression> secondExpression = secondOperand.get();
        offset = secondExpression.end();
        //endregion nary_expression
        
        //region создание AST
        // правоассоциативное преобразование - формируем новый 2й операнд - a op b op c -> a op (b op c)
        BinOpExpression binOp = new BinOpExpression(firstExpression.value(), op.get().value(), secondExpression.value());
        //endregion создание AST
        
        return Optional.of(new ParseResult<>(binOp, start, offset));
    }

    public static void main(String[] args) {
        System.out.println(parseNaryExpression("1", 0));
        //Optional[ParseResult[value=NumValue[value=1.0], start=0, end=1]]
        System.out.println(parseNaryExpression("1 + 2", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=5]]
        System.out.println(parseNaryExpression("1 + 2 + 3", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=BinOpExpression[left=NumValue[value=2.0], op=ADD, right=NumValue[value=3.0]]], start=0, end=9]]
        System.out.println(parseNaryExpression("1 + 2 + 3 + 4", 0));
        //Optional[ParseResult[value=BinOpExpression[left=NumValue[value=1.0], op=ADD, right=BinOpExpression[left=NumValue[value=2.0], op=ADD, right=BinOpExpression[left=NumValue[value=3.0], op=ADD, right=NumValue[value=4.0]]]], start=0, end=13]]
        //
    }

}
