package command.parser;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Парсер команд финансовой системы.
 *
 * command ::= open
 *           | close <account_id>
 *           | deposit <account_id> <amount>
 *           | withdraw <account_id> <amount>
 *           | transfer <account_id> <account_id> <amount>
 *           | balance <account_id>
 *           | list
 *
 * account_id ::= hex-digits '-' hex-digits '-' hex-digits '-' hex-digits '-' hex-digits
 * hex-digits ::= hex-digit { hex-digit }
 * hex-digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" |
 *               "a" | "b" | "c" | "d" | "e" | "f" |
 *               "A" | "B" | "C" | "D" | "E" | "F"
 * 
 * amount ::= [ '-' ] ( digit { digit } | digit { digit } '.' digit { digit } | '.' digit { digit } )
 *            | digit { digit } '.' digit { digit }
 * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 *
 * ws ::= ( " " | "\t" | "\n" | "\r" ) { " " | "\t" | "\n" | "\r" }
 */
public class Parser {

    //region Whitespace Set
    /**
     * Пробельные символы: пробел, табуляция, перенос строки.
     */
    private static final Set<Character> WHITESPACES = Set.of(' ', '\t', '\n', '\r');
    //endregion

    //region parseWhitespace
    /**
     * Парсинг пробельных символов
     * ws ::= ( " " | "\t" | "\n" | "\r" ) { " " | "\t" | "\n" | "\r" }
     *
     * @param source строка для парсинга
     * @param start  начальная позиция
     * @return результат парсинга или Optional.empty()
     */
    public static Optional<ParseResult<String>> parseWhitespace(final String source, final int start) {
        // проверка входных параметров
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        
        // стартовое смещение
        int offset = start;

        // игнорировать конкретные пробельные символы, но увеличивать смещение
        while (offset < source.length() && WHITESPACES.contains(source.charAt(offset))) {
            offset++;
        }

        // строка без символов | пустая, не содержит в том числе пробельных символов
        if (offset == start) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>("", start, offset));
    }
    //endregion
    
    
    //region parseAccountId
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[a-f\\d]{8}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{12}$", Pattern.CASE_INSENSITIVE
    );
    /**
     * Парсинг accountId
     * account_id ::= digit { digit }
     * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     */
    public static Optional<ParseResult<String>> parseAccountId(final String source, final int start) {
        // проверка входных параметров
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        // стартовое смещение
        int offset = start;
        
        // собрать строку
        StringBuilder accountId = new StringBuilder();
        while (offset < source.length() 
                && (Character.isLetterOrDigit(source.charAt(offset)) || source.charAt(offset) == '-')) {
            accountId.append(source.charAt(offset));
            // увеличить смещение, пока строка не завершилась и символы соответствуют грамматике
            offset++;
        }
        
        // не найден UUID
        if (accountId.length() == 0) {
            return Optional.empty();
        }
        
        // не соответствует формату UUID
        if (!UUID_PATTERN.matcher(accountId.toString()).matches()) {
            return Optional.empty();
        }
        
        return Optional.of(new ParseResult<>(accountId.toString(), start, offset));
    }
    //endregion
    
}