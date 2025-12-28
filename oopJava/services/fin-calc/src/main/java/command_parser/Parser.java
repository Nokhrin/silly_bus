package command_parser;

import java.util.Optional;
import java.util.Set;


/**
 * Парсер команд
 * <p>
 * command ::= open
 * | close <account_id>
 * | deposit <account_id> <amount>
 * | withdraw <account_id> <amount>
 * | transfer <account_id> <account_id> <amount>
 * | balance <account_id>
 * | list
 * <p>
 * account_id ::= digit { digit }
 * amount ::= [ '-' ] ( digit { digit } | digit { digit } '.' digit { digit } | '.' digit { digit } )
 * | digit { digit } '.' digit { digit }
 * <p>
 * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * <p>
 * ws ::= ( " " | "\t" | "\n" | "\r" ) { " " | "\t" | "\n" | "\r" }
 */
public class Parser {

    //region Brackets enum

    /**
     * Скобки
     * brackets ::= "(" | ")"
     */
    public enum Brackets {
        OPENING,
        CLOSING
    }
    //endregion

    //region Command enum

    /**
     * Команды
     * command ::= open
     * | close <account_id>
     * | deposit <account_id> <amount>
     * | withdraw <account_id> <amount>
     * | transfer <account_id> <account_id> <amount>
     * | balance <account_id>
     * | list
     */
    public enum Command {
        OPEN,
        CLOSE,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        BALANCE,
        LIST
    }
    //endregion

    //region Whitespace Set
    /**
     * Пробельные символы
     * ws ::= " " | "\t" | "\n" | "\r"
     */
    private static final Set<Character> WHITESPACES = Set.of(
            ' ', '\t', '\n', '\r'
    );

    public static boolean isWhitespace(char ch) {
        return WHITESPACES.contains(ch);
    }
    //endregion

    //region parseBrackets

    /**
     * Парсинг скобок
     * bracket ::= "(" | ")"
     */
    public static Optional<ParseResult<Brackets>> parseBrackets(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        char br = source.charAt(start);

        return switch (br) {
            case '(' -> Optional.of(new ParseResult<>(Brackets.OPENING, start, start + 1));
            case ')' -> Optional.of(new ParseResult<>(Brackets.CLOSING, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseSign

    /**
     * Парсинг знака
     * sign ::= "+" | "-"
     */
    public static Optional<ParseResult<Boolean>> parseSign(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        char ch = source.charAt(start);

        // смещение фиксирую в ParseResult
        return switch (ch) {
            case '+' -> Optional.of(new ParseResult<>(true, start, start + 1));
            case '-' -> Optional.of(new ParseResult<>(false, start, start + 1));
            default -> Optional.empty();
        };
    }
    //endregion

    //region parseWhitespace

    /**
     * Парсинг пробельных символов
     * ws ::= (" " | "\t" | "\n" | "\r") {" " | "\t" | "\n" | "\r"}
     */
    public static Optional<ParseResult<String>> parseWhitespace(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;

        while (offset < source.length() && (isWhitespace(source.charAt(offset)))) {
            offset++;
        }

        if (offset == start) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>("", start, offset));
    }
    //endregion

    //region parseAccountId

    /**
     * Парсер номера счета
     * account_id ::= digit { digit }
     */
    public static Optional<ParseResult<String>> parseAccountId(String source, int start) {
        // стандартная проверка исходной строки и индекса
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    //endregion

    //region parseAmount

    /**
     * Парсер суммы
     * amount ::= [ '-' ] ( digit { digit } | digit { digit } '.' digit { digit } | '.' digit { digit } )
     * | digit { digit } '.' digit { digit }
     */
    public static Optional<ParseResult<Amount>> parseAmount(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    //endregion


    //region parseCommand

    /**
     * Парсер команды
     * command ::= open
     * | close <account_id>
     * | deposit <account_id> <amount>
     * | withdraw <account_id> <amount>
     * | transfer <account_id> <account_id> <amount>
     * | balance <account_id>
     * | list
     */
    public static Optional<ParseResult<Command>> parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.empty();
    }
    //endregion
}
