package Calculator;

import java.util.Optional;

/**
 * Напиши функцию парсинга числа , целое
 * 
 * eBNF
 * ===
 * 1 знак или +, или -
 * 1+ цифра - строка с цифрой от 0 до 9
 * число - знак? цифры в количестве от 1 и более
 * 
 * sign   ::= "+" | "-"
 * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * digits ::= [sign] digit { digit }*
 * 
 * требования к парсеру  
 *     Не парсит пустые строки 
 *     Умеет обрабатывать  +,  -
 *          - >1 знака подряд => ошибка?
 *     Останавливается на первом неправильном символе 
 *     Правильно работает с границами  int
 *     Использует  Optional
 *     методы public static
 *     обобщенные типы для значения, полученного парсингом
 *      - Boolean для знака, Integer для числа
 *     чистые функции
 */
public class IntegerLexer {

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
     * digits ::= [sign] digit {digit}*
     *  
     */
    public static Optional<ParseResult<Integer>> parseInt(Optional<String> source, int start) {
        //region digits
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
        //endregion
    }
    //endregion

    //region ENTRY POINT
    public static void main(String[] args) {
        Optional<String> source = Optional.of("-456");
        Optional<ParseResult<Integer>> result = IntegerLexer.parseInt(source, 0);
        System.out.println(result); // Optional[ParseResult[value=-456, start=0, end=4]]
    }
    //endregion
}
