package Calculator;

import java.util.Optional;

/*

 * Пример - примитивная конструкция совпадения 
 *
 * <true> ::= "true"
 *
 * задано правило true
 * соответственно должна быть функция
 *
 * Optional<Result<Object>> parseTrue( String source, int offset )
 *
 * функция должна проверить что в позиции offset исходника source присутствует текст "true"
 * и если так, то должна вернуть Result , где value = любое значение, beginOffset = offset, endOffset = offset + "true".length()
 * 
 * 
 * Пример Оператор или - Вертикальная черта
 *
 * <boolean> ::= "true" | "false"
 *
 * должна быть функция
 *
 * Optional<Result<Boolean>> parseBoolean( String source, int offset )
 *
 * функция должна проверить что в позиции присутствует true или offset, и если так, то возвращает
 * Result value = true иди false, beginOffset = offset, endOffset = offset + (4 или 5)
 */

public class BooleanParser {
    /** 
     * Парсит true|false начиная с позиции offset
     * 
     * @param source - строка для парсинга
     * @param offset - индекс элемента начала парсинга
     * @return Optional&lt;ParseResult&lt;Boolean&gt;&gt;
     */
    public static Optional<ParseResult<Boolean>> parseBoolean(Optional<String> source, int offset) {
        // применяю flatMap для проверки количества символов в строке
        Optional<Boolean> check = source
                .flatMap(v -> {
                    if (offset < 0 || v.length() > offset) {
                        return Optional.empty();
                    }
                    return Optional.of(true);
                });

        Optional<String> lowStr = source
                .flatMap(v -> {
                    return Optional.of(v.substring(offset).toLowerCase());
                });

        return lowStr
                .flatMap(v -> {
                    if (v.startsWith("true")) {
                        return Optional.of(new ParseResult<>(true, offset, offset + 4));
                    } else if (v.startsWith("false")) {
                        return Optional.of(new ParseResult<>(false, offset, offset + 5));
                    } else {
                        return Optional.empty();
                    }
                });
    }

    public static void main(String[] args) {
        Optional<String> s1 = Optional.of("true");
        Optional<String> s2 = Optional.of("FALSE");
        
        System.out.println(parseBoolean(s1, 0)); // Optional[ParseResult[value=true, start=0, end=4]]
        System.out.println(parseBoolean(s2, 0)); // Optional[ParseResult[value=false, start=0, end=5]]
    }
}
