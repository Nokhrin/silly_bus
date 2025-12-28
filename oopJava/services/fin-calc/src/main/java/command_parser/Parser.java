package command_parser;

import java.math.BigDecimal;
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
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;

        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            offset++;
        }

        // нет цифр
        if (offset == start) {
            return Optional.empty();
        }

        String id = source.substring(start, offset);
        return Optional.of(new ParseResult<>(id, start, offset));
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
        int offset = start;
        boolean negative = false;

        // [ '-' ]
        if (source.charAt(offset) == '-') {
            negative = true;
            offset++;
        } else if (source.charAt(offset) == '+') {
            offset++;
        }

        // проверка, что еще есть символы
        if (offset >= source.length()) {
            return Optional.empty();
        }

        boolean hasDigits = false;
        boolean hasDecimalPoint = false;
        int digitsStart = offset;

        // Собираем цифры до точки
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            hasDigits = true;
            offset++;
        }

        // Точка
        if (offset < source.length() && source.charAt(offset) == '.') {
            hasDecimalPoint = true;
            offset++;
            while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
                offset++;
            }
        }

        // Если нет цифр, а только точка — ошибка
        if (!hasDigits && !hasDecimalPoint) {
            return Optional.empty();
        }

        // Если только точка, например ".50"
        if (hasDigits || hasDecimalPoint) {
            String amountStr = source.substring(start, offset);
            try {
                BigDecimal value = new BigDecimal(amountStr);
                if (negative) {
                    value = value.negate();
                }
                return Optional.of(new ParseResult<>(new Amount(value), start, offset));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
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
    public static Optional<ParseResult<Parser.Command>> parseCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Optional.empty();
        }

        return Optional.empty();
    }
    //endregion
    
    // region parse

    /**
     * Парсинг всей команды
     */
    public static Optional<ParseResult<Operation>> parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Optional.empty();
        }

        String trimmed = input.trim();
        Optional<ParseResult<Command>> cmdResult = parseCommand(trimmed);
        if (cmdResult.isEmpty()) {
            return Optional.empty();
        }

        ParseResult<Command> cmd = cmdResult.get();
        Command command = cmd.value();

        // Обработка аргументов
        String[] parts = trimmed.substring(cmd.end()).trim().split("\\s+", 3);
        
        switch (command) {
            case OPEN -> {
                return Optional.of(new ParseResult<>(new Operation("OPEN", BigDecimal.ZERO, null, null), 0, trimmed.length()));
            }
            case CLOSE -> {
                if (parts.length == 0) return Optional.empty();
                return Optional.of(new ParseResult<>(new Operation("CLOSE", BigDecimal.ZERO, Integer.parseInt(parts[0]), null), 0, trimmed.length()));
            }
            case DEPOSIT -> {
                if (parts.length < 2) return Optional.empty();
                Integer id = Integer.parseInt(parts[0]);
                Optional<ParseResult<Amount>> amountResult = parseAmount(trimmed, cmd.end());
                if (amountResult.isEmpty()) return Optional.empty();
                Amount amount = amountResult.get().value();
                return Optional.of(new ParseResult<>(new Operation("DEPOSIT", amount.value(), id, null), 0, trimmed.length()));
            }
            case WITHDRAW -> {
                if (parts.length < 2) return Optional.empty();
                Integer id = Integer.parseInt(parts[0]);
                Optional<ParseResult<Amount>> amountResult = parseAmount(trimmed, cmd.end());
                if (amountResult.isEmpty()) return Optional.empty();
                Amount amount = amountResult.get().value();
                return Optional.of(new ParseResult<>(new Operation("WITHDRAW", amount.value(), id, null), 0, trimmed.length()));
            }
            case TRANSFER -> {
                if (parts.length < 3) return Optional.empty();
                Integer fromId = Integer.parseInt(parts[0]);
                Integer toId = Integer.parseInt(parts[1]);
                Optional<ParseResult<Amount>> amountResult = parseAmount(trimmed, cmd.end());
                if (amountResult.isEmpty()) return Optional.empty();
                Amount amount = amountResult.get().value();
                return Optional.of(new ParseResult<>(new Operation("TRANSFER", amount.value(), fromId, toId), 0, trimmed.length()));
            }
            case BALANCE -> {
                if (parts.length < 1) return Optional.empty();
                Integer id = Integer.parseInt(parts[0]);
                return Optional.of(new ParseResult<>(new Operation("BALANCE", BigDecimal.ZERO, id, null), 0, trimmed.length()));
            }
            case LIST -> {
                return Optional.of(new ParseResult<>(new Operation("LIST", BigDecimal.ZERO, null, null), 0, trimmed.length()));
            }
        }
        return Optional.empty();
    }
    // endregion
}
