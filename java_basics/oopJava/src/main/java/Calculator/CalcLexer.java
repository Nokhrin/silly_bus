package Calculator;

import java.util.Optional;

/**
 * Написать функции парсинга для лексера
 * 
 * 1
 * 
 * Число :== [sign] digit {digits}
 * Пробел :== Пробел {пробел+таб}
 * 
 * {digits}, {пробел+таб} - нетерминалы => вызовы других парсеров
 * 
 * Терминалы
 * Оператор ::== "+" | "-" | "*" | "/"
 * Скобки ::== "(" | ")"
 * Знак ::== "+" | "-"
 * Цифра ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * 
 * ---
 * 
 * 2
 * 
 * Все правила грамматики
 * название правила :== псевдокод правила
 * 
 * - методы public static
 * - возвращают Optional<ParseResult<Boolean>>, Optional<ParseResult<Integer>>
 * - принимают два аргумента (String source, int offset)
 * - результат вызова (offset) вложенного правила используется в последующем вызове
 * 
 * 
 * @todo проверить понимание
 * калькулятор поддерживает
 *  - целые числа положительные и отрицательные
 *  - oператоры "+" | "-" | "*" | "/"
 *      - приоритеты операций "*", "/", "+", "-"
 *  - вложенные выражения / выражения в скобках
 * 
 * eBNF правила для калькулятора
 * 
 * sign ::= "+" | "-"
 * 
 * - считаю рациональным решением - разделить операторы на группы 
 *  - "операторы умножения"
 *  - "операторы сложения"
 *  - довод - упрощение проверки приоритета операций => упрощение логики парсинга
 *      - multOp высший приоритет, addOp - следующий
 * mul_op ::= "*" | "/"
 * add_op ::= "+" | "-"
 * 
 * соглашения:
 *  - Выражение - expr
 *  - Выражение Операции сложения и вычитания - add_expr
 *  - Выражение Операции умножения и деления - mul_expr
 *  - текст после `//` есть комментарий
 * 
 * // нетерминалы
 * expr :== add_expr        // Любое Выражение есть Выражение Операции сложения и вычитания
 * add_expr :== mul_expr | add_expr add_op mul_expr     
 *      // Выражение Операции сложения и вычитания есть 
 *        // Выражение Операции умножения и деления  
 *        // ИЛИ
 *        // Выражение Операции сложения и вычитания, 
 *              за которым следует Символ оператора + или -, 
 *              за которым следует Выражение Операции умножения и деления
 * mul_expr :== main_expr | mul_expr mul_op main_expr
 *      // Выражение Операции умножения и деления есть 
 *        // Основное выражение  
 *        // ИЛИ
 *        // Выражение Операции умножения и деления, 
 *              за которым следует Символ оператора * или /, 
 *              за которым следует Основное выражение
 *              
 * main_expr :== num | "(" expr ")" | [sign] main_expr  
 *          // основное выражение - 
 *              число
 *              или 
 *              скобка выражение скобка
 *              или 
 *              знак/нет знака основное выражение 
 * num :== digit {digit}    // число - цифра от одной и более
 * 
 * // терминалы
 * mul_op ::= "*" | "/"     // "операторы умножения"
 * add_op ::= "+" | "-"     // "операторы сложения"
 * sign ::= "+" | "-"     // знак числа
 * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 */
public class CalcLexer {

    //region ParseResult record
    /**
     * Объект результата парсинга
     *
     * Применяю record - контейнер неизменяемых данных
     * поля immutable, как если бы объявил класс с private final
     * методы equals, hashCode и toString создаются по умолчанию
     *
     * Применяется обобщение для типа значения лексемы
     *  - допускается Integer, Decimal для чисел, String для операторов, пробелов
     */
    public record ParseResult<T> (
            T value,
            int start,
            int end
    ) { }
    //endregion


    //region Operations enum
    /**
     * Арифметические операции
     * 
     * brackets ::== "(" | ")"
     */
    public enum Brackets {
        OPENING,
        CLOSING
    }
    //endregion

    //region Brackets enum
    /**
     * Скобки
     * 
     * operations ::= "+" | "-" | "*" | "/"
     */
    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    //endregion


    public static void parseSignedInt(Optional<String> source, Optional<ParseResult> result) {

    }

    //region parseSign
    /**
     * Парсинг знака
     * sign       ::= "+" | "-"
     *
     * Используй результат Optional<ParseResult> для расчета offset
     *  ParseResult содержит 
     *      значение, 
     *      первый (включительно),
     *      последний (исключительно) индексы значения
     * Инкремент запрещен
     *
     * используй комментирование регионов кода
     */
    public static Optional<ParseResult<Boolean>> parseSign(Optional<String> source, int start) {
        //region "+" | "-"
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        char ch = src.charAt(start);

        // смещение фиксирую в ParseResult
        return switch (ch) {
            case '+' -> Optional.of(new ParseResult<>(true, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(false, start, start + 1));
            default -> Optional.empty();
        };
        //endregion
    }
    //endregion

    //region parseInt
    /**
     * Парсинг цифр
     *
     * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     * digits ::= [sign] digit {digit}
     *
     */
    public static Optional<ParseResult<Integer>> parseInt(Optional<String> source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        int offset = start;
        boolean negative = false;

        // знак с помощью parseSign
        Optional<ParseResult<Boolean>> sign = parseSign(Optional.of(src), offset);
        if (sign.isPresent()) {
            negative = !sign.get().value();
            offset = sign.get().end(); // смещаю курсор на символ знака
        }

        // цифры
        // курсор в пределах строки и после знака идет цифра
        if (offset >= src.length() || !Character.isDigit(src.charAt(offset))) { return Optional.empty(); }

        // читаю цифры
        int num = 0;
        int initialOffset = offset;

        while (offset < src.length() && Character.isDigit(src.charAt(offset))) {
            num = num * 10 + (src.charAt(offset) - '0'); // прием с `- '0'` для преобразования в int
            offset++;
        }

        // цифр нет
        if (offset == initialOffset) { return Optional.empty(); }

        // применяю знак
        if (negative) {
            num = -1 * num;
        }

        return Optional.of(new ParseResult<>(num, start, offset));
    }
    //endregion

    //region parseBrackets
    /**
     * Парсинг скобок
     *
     * рассматриваю правило
     * bracket ::= "(" | ")"
     *  - говорит: bracket есть или открывающая скобка, или закрывающая скобка
     *  - НЕ гарантирует парность
     *  - НЕ описывает вложенность
     */
    public static Optional<ParseResult<Brackets>> parseBrackets(Optional<String> source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        char br = src.charAt(start);

        return switch (br) {
            case '(' -> Optional.of(new ParseResult<>(Brackets.OPENING, start, start + 1));
            case ')' -> Optional.of(new ParseResult<>(Brackets.CLOSING, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion
    
    //region parseOperation
    /**
     * Парсинг оператора
     *
     * whitespace ::= [ " " | "\t" ]+
     *
     */
    public static Optional<ParseResult<Operation>> parseOperation(Optional<String> source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        char op = src.charAt(start);
        
        return switch (op) {
            case '*' -> Optional.of(new ParseResult<>(Operation.MUL, start, start + 1));
            case '/' -> Optional.of(new ParseResult<>(Operation.DIV, start, start + 1));
            case '+' -> Optional.of(new ParseResult<>(Operation.ADD, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(Operation.SUB, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseWhitespace
    /**
     * Парсинг одного или более пробелов и/или табуляций
     *
     */
    public static Optional<ParseResult<String>> parseWhitespace(Optional<String> source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.get().length()) { return Optional.empty(); }

        String src = source.get();
        int offset = start;
        
        // последовательность пробелов и табуляций
        while (offset < src.length() &&
                (src.charAt(offset) == ' ' || src.charAt(offset) == '\t')) {
            offset++;
        }
        
        // пробелов | табуляций нет
        if (offset == start) return Optional.empty();
        
        return Optional.of(new ParseResult<>("", start, offset));
    }
    //endregion



    //region ENTRY POINT
    public static void main(String[] args) {
        // проверка парсинга числа
        Optional<String> source = Optional.of("-456");
        Optional<ParseResult<Integer>> result = parseInt(source, 0);
        System.out.println(result); // Optional[ParseResult[value=-456, start=0, end=4]]
        
        // проверка парсинга знака
        Optional<ParseResult<Boolean>> resultSign = parseSign(Optional.of("-"), 0);
        System.out.println(resultSign); // Optional[ParseResult[value=false, start=0, end=1]]
        
        // проверка парсинга операции
        Optional<ParseResult<Operation>> resultOp = parseOperation(Optional.of("*"), 0);
        System.out.println(resultOp); // Optional[ParseResult[value=MUL, start=0, end=1]]
        
        // проверка парсинга скобки
        Optional<ParseResult<Brackets>> resultBr = parseBrackets(Optional.of("("), 0);
        System.out.println(resultBr); // Optional[ParseResult[value=OPENING, start=0, end=1]]
        resultBr = parseBrackets(Optional.of(")"), 0);
        System.out.println(resultBr); // Optional[ParseResult[value=CLOSING, start=0, end=1]]
        
        // проверка парсинга пробела
        Optional<ParseResult<String>> resultWs = parseWhitespace(Optional.of("    hello"), 0);
        System.out.println(resultWs); // Optional[ParseResult[value=, start=0, end=4]]
        System.out.println(resultWs.get().value()); // ""
        
        
    }
    //endregion
}
