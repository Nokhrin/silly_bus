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
// назначение?
//    /**
//     * Команды
//     * command ::= 
//     *   open
//     * | close <account_id>
//     * | deposit <account_id> <amount>
//     * | withdraw <account_id> <amount>
//     * | transfer <account_id> <account_id> <amount>
//     * | balance <account_id>
//     * | list
//     */
//    public enum Command {
//        open,
//        close,
//        deposit,
//        withdraw,
//        transfer,
//        balance,
//        list
//    }
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
    public static Optional<ParseResult<BigDecimal>> parseAmount(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;
        boolean negative = false;

        if (source.charAt(offset) == '-') {
            negative = true;
            offset++;
        }

        if (offset >= source.length()) {
            return Optional.empty();
        }

        boolean hasDigits = false;
        boolean hasDecimalPoint = false;
        boolean hasAfterPoint = false;

        // Цифры до точки
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            hasDigits = true;
            offset++;
        }

        // Точка
        if (offset < source.length() && source.charAt(offset) == '.') {
            hasDecimalPoint = true;
            offset++;
            while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
                hasAfterPoint = true;
                offset++;
            }
        }

        // Проверка: если есть точка, то либо до, либо после — цифры
        if (hasDecimalPoint && !hasDigits && !hasAfterPoint) {
            return Optional.empty();
        }

        // Проверка на остаток
        if (offset < source.length() && !Character.isDigit(source.charAt(offset)) && source.charAt(offset) != '.') {
            return Optional.empty();
        }

        String amountStr = source.substring(start, offset);

        try {
            BigDecimal value = new BigDecimal(amountStr);
            if (negative) {
                value = value.negate();
            }
            return Optional.of(new ParseResult<>(value, start, offset));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    //endregion

    
    
    //region parseCommandName
    public static Optional<ParseResult<String>> parseCommandName(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;
        while (offset < source.length() && !Character.isWhitespace(source.charAt(offset))) {
            offset++;
        }

        if (offset == start) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(source.substring(start, offset), start, offset));
    }
    //endregion parseCommandName


    //region Commands

    public static Optional<ParseResult<Command>> parseOpen(String source, int start) {
        if (source.isEmpty() || start < 0 || start > source.length()) {
            return Optional.empty();
        }

        // команда
        if (!source.startsWith("open", 0)) {
            return Optional.empty();
        }

        int end = start + 4;

        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isEmpty()) {
            return Optional.of(new ParseResult<>(new Open(), start, end));
        }
        end = ws.get().end();

        // аргументов не ожидается
        if (end < source.length()) {
            return Optional.empty();
        }


        return Optional.of(new ParseResult<>(new Open(), start, end));
    }

    public static Optional<ParseResult<Command>> parseClose(String source, int start) {
        if (source.isEmpty() || start < 0 || start > source.length()) {
            return Optional.empty();
        }
    
        // команда
        if (!source.startsWith("close", 0)) {
            return Optional.empty();
        }

        int end = start + 5;
        
        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isPresent()) {
            end = ws.get().end();
        }

        // accountId
        Optional<ParseResult<String>> idResult = parseAccountId(source, end);
        if (idResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new Close(), start, idResult.get().end()));

    }

    public static Optional<ParseResult<Command>> parseDeposit(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        // команда
        if (!source.startsWith("deposit", 0)) {
            return Optional.empty();
        }

        int end = start + 7;

        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isPresent()) {
            end = ws.get().end();
        }


        // accountId
        Optional<ParseResult<String>> idResult = parseAccountId(source, end);
        if (idResult.isEmpty()) {
            return Optional.empty();
        }

        // amount
        Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, idResult.get().end());
        if (amountResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new Deposit(), start, amountResult.get().end()));
    }

    public static Optional<ParseResult<Command>> parseWithdraw(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        // команда
        if (!source.startsWith("withdraw", 0)) {
            return Optional.empty();
        }

        int end = start + 7;
        
        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isPresent()) {
            end = ws.get().end();
        }

        
        // accountId
        Optional<ParseResult<String>> idResult = parseAccountId(source, end);
        if (idResult.isEmpty()) {
            return Optional.empty();
        }

        // amount
        Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, idResult.get().end());
        if (amountResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new Withdraw(), start, amountResult.get().end()));
    }

    public static Optional<ParseResult<Command>> parseTransfer(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        // команда
        if (!source.startsWith("transfer", 0)) {
            return Optional.empty();
        }

        int end = start + 8;

        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isPresent()) {
            end = ws.get().end();
        }


        // accountIdSource
        Optional<ParseResult<String>> accountIdSource = parseAccountId(source, end);
        if (accountIdSource.isEmpty()) {
            return Optional.empty();
        }

        // accountIdTarget
        Optional<ParseResult<String>> accountIdTarget = parseAccountId(source, accountIdSource.get().end());
        if (accountIdTarget.isEmpty()) {
            return Optional.empty();
        }

        Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, accountIdTarget.get().end());
        if (amountResult.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new Transfer(), start, amountResult.get().end()));
    }

    public static Optional<ParseResult<Command>> parseBalance(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        // команда
        if (!source.startsWith("balance", 0)) {
            return Optional.empty();
        }

        int end = start + 6;
        
        // ws
        Optional<ParseResult<String>> ws = parseWhitespace(source, end);
        if (ws.isPresent()) {
            end = ws.get().end();
        }


        // accountId
        Optional<ParseResult<String>> accountId = parseAccountId(source, end);
        if (accountId.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new Balance(), start, accountId.get().end()));
    }

    public static Optional<ParseResult<Command>> parseList(String source, int start) {
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        if (!source.startsWith("list", 0)) {
            return Optional.empty();
        }

        int end = start + 4;
        if (end < source.length() && !isWhitespace(source.charAt(end))) {
            return Optional.empty();
        }

        return Optional.of(new ParseResult<>(new List(), start, end));
    }

    //region parseCommand
    /**
     * command ::= open
     * | close <account_id>
     * | deposit <account_id> <amount>
     * | withdraw <account_id> <amount>
     * | transfer <account_id> <account_id> <amount>
     * | balance <account_id>
     * | list
     * @param source
     * @param start
     * @return
     */
    public static Optional<ParseResult<Command>> parseCommand(String source, int start) {
        if (source.isEmpty() || start < 0 || start > source.length()) {
            return Optional.empty();
        }

        int offset = start;

        Optional<ParseResult<String>> commandName = parseCommandName(source, offset);
        if (commandName.isEmpty()) {
            return Optional.empty();
        }

        return switch (commandName.get().value().toLowerCase()) {
            case "open" -> parseOpen(source, commandName.get().end());
            case "close" -> parseClose(source, commandName.get().end());
            case "deposit" -> parseDeposit(source, commandName.get().end());
            case "withdraw" -> parseWithdraw(source, commandName.get().end());
            case "transfer" -> parseTransfer(source, commandName.get().end());
            case "balance" -> parseBalance(source, commandName.get().end());
            case "list" -> parseList(source, commandName.get().end());
            default -> Optional.empty();
        };
    }
    //endregion parseCommand
}