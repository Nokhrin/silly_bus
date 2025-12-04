package CalcLexer;

import java.util.Optional;
import java.util.SplittableRandom;

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
    public static Optional<ParseResult<Boolean>> parseSign(Optional<String> source, int start) {
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
    }
    //endregion

    //region parseInt
    /**
     * Парсинг целого числа
     * int ::= [sign] digit {digit}
     * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     */
    public static Optional<ParseResult<NumValue>> parseInt(Optional<String> source, int start) {
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
            // `digitAsChar - '0'` - для преобразования в int
            // это выражение - неявно - выполняет игнорирование ведущих нулей
            num = num * 10 + (src.charAt(offset) - '0');
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
     * op ::= "+" | "-" | "*" | "/"
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
     * ws ::= (" " | "\t")+
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
        Optional<ParseResult<NumValue>> result = parseInt(source, 0);
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
        //
        // ??? такой способ проверки существования value в контейнере Optional - норм?
        //
        resultWs.ifPresent(wsParsed -> System.out.println(wsParsed.value()));
        
        
    }
    //endregion
}
