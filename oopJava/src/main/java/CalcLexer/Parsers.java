package CalcLexer;

import java.util.Optional;

/**
 * Требования
 * Используй результат Optional<ParseResult> для расчета offset
 *  ParseResult содержит 
 *      значение, 
 *      первый (включительно),
 *      последний (исключительно) индексы значения
 * Инкремент запрещен
 *
 * используй комментирование регионов кода
 */
public class Parsers {

    //region Brackets enum
    /**
     * Скобки - группировка, определение вложенности выражений
     * brackets ::= "(" | ")"
     * @see #parseBrackets
     */
    public enum Brackets {
        OPENING,
        CLOSING
    }
    //endregion

    //region Operation enum
    /**
     * Арифметические операторы
     * operations ::= "+" | "-" | "*" | "/"
     */
    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }
    //endregion

    //region parseSign
    /**
     * Парсинг знака
     * sign ::= "+" | "-"
     */
    public static Optional<ParseResult<Boolean>> parseSign(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char ch = source.charAt(start);

        // смещение фиксирую в ParseResult
        return switch (ch) {
            case '+' -> Optional.of(new ParseResult<>(true, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(false, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseInt
    /**
     * Парсинг целого числа
     * int ::= [sign] digit {digit}
     * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     */
    public static Optional<ParseResult<NumValue>> parseNumber(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;
        boolean negative = false;

        // знак с помощью parseSign
        Optional<ParseResult<Boolean>> sign = parseSign(source, offset);
        if (sign.isPresent()) {
            negative = !sign.get().value();
            offset = sign.get().end(); // смещаю курсор на символ знака
        }

        // цифры
        // курсор в пределах строки и после знака идет цифра
        if (offset >= source.length() || !Character.isDigit(source.charAt(offset))) { return Optional.empty(); }

        // читаю цифры
        int num = 0;
        int initialOffset = offset;

        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            // `digitAsChar - '0'` - для преобразования в int
            // это выражение - неявно - выполняет игнорирование ведущих нулей
            num = num * 10 + (source.charAt(offset) - '0');
            offset++;
        }

        // цифр нет
        if (offset == initialOffset) { return Optional.empty(); }

        // применяю знак
        if (negative) {
            num = -1 * num;
        }

        return Optional.of(new ParseResult<>(new NumValue(num), start, offset));
    }
    //endregion

    //region parseBrackets
    /**
     * Парсинг скобок
     * bracket ::= "(" | ")"
     */
    public static Optional<ParseResult<Brackets>> parseBrackets(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char br = source.charAt(start);

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
     * op ::= "+" | "-" | "*" | "/"
     */
    public static Optional<ParseResult<Operation>> parseOperation(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        char op = source.charAt(start);
        
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
     * ws ::= (" " | "\t") {" " | "\t"}
     */
    public static Optional<ParseResult<String>> parseWhitespace(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) { return Optional.empty(); }

        int offset = start;
        
        if (source.charAt(offset) == ' ' || source.charAt(offset) == '\t') {
            offset++;
        } else {
            return Optional.empty();
        }
        
        while (offset < source.length() && (source.charAt(offset) == ' ' || source.charAt(offset) == '\t')) {
            offset++;
        }
        
        return Optional.of(new ParseResult<>("", start, offset));
    }
    //endregion



    //region ENTRY POINT
    public static void main(String[] args) {
        // проверка парсинга числа
        Optional<ParseResult<NumValue>> result = parseNumber("-456", 0);
        System.out.println(result); // Optional[ParseResult[value=-456, start=0, end=4]]
        
        // проверка парсинга знака
        Optional<ParseResult<Boolean>> resultSign = parseSign("-", 0);
        System.out.println(resultSign); // Optional[ParseResult[value=false, start=0, end=1]]
        
        // проверка парсинга операции
        Optional<ParseResult<Operation>> resultOp = parseOperation("*", 0);
        System.out.println(resultOp); // Optional[ParseResult[value=MUL, start=0, end=1]]
        
        // проверка парсинга скобки
        Optional<ParseResult<Brackets>> resultBr = parseBrackets("(", 0);
        System.out.println(resultBr); // Optional[ParseResult[value=OPENING, start=0, end=1]]
        resultBr = parseBrackets(")", 0);
        System.out.println(resultBr); // Optional[ParseResult[value=CLOSING, start=0, end=1]]
        
        // проверка парсинга пробела
        Optional<ParseResult<String>> resultWs = parseWhitespace("    hello", 0);
        System.out.println(resultWs); // Optional[ParseResult[value=, start=0, end=4]]
        //
        // ??? такой способ проверки существования value в контейнере Optional - норм?
        //
        resultWs.ifPresent(wsParsed -> System.out.println(wsParsed.value()));
        
    }
    //endregion
}
