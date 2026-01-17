package command.parser;

import java.math.BigDecimal;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Парсер команд финансовой системы.
 * Спецификация синтаксиса команд находится в файле:
 * src/main/resources/docs/financial-operations-grammar.md
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
     * account_id ::= hex-digits '-' hex-digits '-' hex-digits '-' hex-digits '-' hex-digits
     * hex-digits ::= hex-digit { hex-digit }
     * hex-digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" |
     * "a" | "b" | "c" | "d" | "e" | "f" |
     * "A" | "B" | "C" | "D" | "E" | "F"
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
        while (offset < source.length()) {
            char c = source.charAt(offset);
            if (
                    (c >= '0' && c <= '9') ||
                            (c >= 'a' && c <= 'f') ||
                            (c >= 'A' && c <= 'F') ||
                            c == '-'
            ) {
                accountId.append(source.charAt(offset));
                // увеличить смещение, пока строка не завершилась и символы соответствуют грамматике
                offset++;
            } else {
                break;
            }
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

    //region parseAmount

    /**
     * Парсит сумму
     * <p>
     * amount ::= [ '-' ] ( digit { digit } | digit { digit } '.' digit { digit } | '.' digit { digit } )
     * | digit { digit } '.' digit { digit }
     * digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     */
    public static Optional<ParseResult<BigDecimal>> parseAmount(final String source, final int start) {
        // проверка входных параметров  
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;
        boolean isNegative = false;

        // Шаг 1: Проверка на знак минуса
        if (source.charAt(offset) == '-') {
            isNegative = true;
            offset++;
        }

        // Шаг 2: Проверка на наличие цифр перед точкой или после неё
        // Возможные форматы:
        // 1. "123" → целое число
        // 2. "123.45" → дробь с целой частью
        // 3. ".45" → дробь без целой части
        // 4. "-123", "-123.45", "-.45"

        boolean hasDigitsBeforeDot = false;
        boolean hasDigitsAfterDot = false;
        int digitsBeforeDot = 0;
        int digitsAfterDot = 0;

        // Сбор цифр до точки
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            digitsBeforeDot++;
            offset++;
        }

        if (digitsBeforeDot > 0) {
            hasDigitsBeforeDot = true;
        }

        // Проверка на точку
        boolean hasDot = false;
        if (offset < source.length() && source.charAt(offset) == '.') {
            hasDot = true;
            offset++;
            // Сбор цифр после точки
            while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
                digitsAfterDot++;
                offset++;
            }
            if (digitsAfterDot > 0) {
                hasDigitsAfterDot = true;
            }
        }

        // Проверка: должно быть либо цифры до точки, либо после, либо и то, и другое
        if (!hasDigitsBeforeDot && !hasDigitsAfterDot) {
            return Optional.empty(); // Нет цифр, ни до, ни после точки
        }

        // Собираем строку для парсинга BigDecimal
        StringBuilder amountStr = new StringBuilder();
        if (isNegative) {
            amountStr.append('-');
        }

        // Восстанавливаем строку
        int endOffset = offset;
        int tempOffset = start;
        if (isNegative) {
            tempOffset++;
        }

        // Собираем часть до точки
        while (tempOffset < source.length() && Character.isDigit(source.charAt(tempOffset))) {
            amountStr.append(source.charAt(tempOffset));
            tempOffset++;
        }

        // Добавляем точку, если есть
        if (hasDot) {
            amountStr.append('.');
            tempOffset++;
            while (tempOffset < source.length() && Character.isDigit(source.charAt(tempOffset))) {
                amountStr.append(source.charAt(tempOffset));
                tempOffset++;
            }
        }

        // Попытка парсинга BigDecimal
        try {
            BigDecimal parsedAmount = new BigDecimal(amountStr.toString());
            return Optional.of(new ParseResult<>(parsedAmount, start, offset));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    //endregion


    //region parseWord
    private static String parseWord(String source, int start) {
        if (start >= source.length()) return null;

        int end = start;
        while (end < source.length() && !Character.isWhitespace(source.charAt(end)) && source.charAt(end) != '-') {
            end++;
        }

        if (end == start) return null;

        return source.substring(start, end);
    }
    //endregion

    //region parseCommand

    /**
     * Парсер команд.
     * <p>
     * command ::=
     * open
     * | close <account_id>
     * | deposit <account_id> <amount>
     * | withdraw <account_id> <amount>
     * | transfer <account_id> <account_id> <amount>
     * | balance <account_id>
     * | list-accounts
     */
    public static Optional<ParseResult<Command>> parseCommand(final String source, final int start) {
        // проверка входных параметров    
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }

        int offset = start;

        // Шаг 1: Пропускаем пробелы
        Optional<ParseResult<String>> wsResult = parseWhitespace(source, offset);
        if (wsResult.isEmpty()) {
            // Нет пробелов - начинаем парсинг команды
            offset = start;
        } else {
            offset = wsResult.get().end();
        }

        // Шаг 2: Определяем тип команды по первому слову
        String commandText = parseWord(source, offset);
        if (commandText == null) {
            return Optional.empty();
        }

        offset = commandText.length() + offset; // смещение после слова

        // Шаг 3: Парсим команду
        return switch (commandText) {
            case "open-account" -> {
                // open-account - без аргументов
                Optional<ParseResult<String>> wsAfter = parseWhitespace(source, offset);
                if (wsAfter.isPresent()) {
                    // Допускается пробел после open, но не обязательно
                    offset = wsAfter.get().end();
                }

                yield Optional.of(new ParseResult<>(new OpenAccount(), start, offset));
            }
            case "close" -> {
                // close-account <account_id>
                Optional<ParseResult<String>> wsAfterClose = parseWhitespace(source, offset);
                if (wsAfterClose.isEmpty()) {
                    yield Optional.empty(); // нет места для account_id
                }
                offset = wsAfterClose.get().end();

                Optional<ParseResult<String>> accountIdResult = parseAccountId(source, offset);
                if (accountIdResult.isEmpty()) {
                    yield Optional.empty();
                }

                UUID accountId = UUID.fromString(accountIdResult.get().value());
                offset = accountIdResult.get().end();

                Optional<ParseResult<String>> wsAfterAccountId = parseWhitespace(source, offset);
                if (wsAfterAccountId.isPresent()) {
                    offset = wsAfterAccountId.get().end();
                }

                yield Optional.of(new ParseResult<>(new CloseAccount(accountId), start, offset));
            }

            case "deposit" -> {
                // deposit <account_id> <amount>
                Optional<ParseResult<String>> wsAfterDeposit = parseWhitespace(source, offset);
                if (wsAfterDeposit.isEmpty()) {
                    yield Optional.empty();
                }
                offset = wsAfterDeposit.get().end();

                Optional<ParseResult<String>> accountIdResult = parseAccountId(source, offset);
                if (accountIdResult.isEmpty()) {
                    yield Optional.empty();
                }
                UUID accountId = UUID.fromString(accountIdResult.get().value());
                offset = accountIdResult.get().end();

                Optional<ParseResult<String>> wsAfterAccountId = parseWhitespace(source, offset);
                if (wsAfterAccountId.isPresent()) {
                    offset = wsAfterAccountId.get().end();
                }

                Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, offset);
                if (amountResult.isEmpty()) {
                    yield Optional.empty();
                }
                BigDecimal amount = amountResult.get().value();
                offset = amountResult.get().end();

                yield Optional.of(new ParseResult<>(new Deposit(accountId, amount), start, offset));
            }

            case "withdraw" -> {
                // withdraw <account_id> <amount>
                Optional<ParseResult<String>> wsAfterWithdraw = parseWhitespace(source, offset);
                if (wsAfterWithdraw.isEmpty()) {
                    yield Optional.empty();
                }
                offset = wsAfterWithdraw.get().end();

                Optional<ParseResult<String>> accountIdResult = parseAccountId(source, offset);
                if (accountIdResult.isEmpty()) {
                    yield Optional.empty();
                }
                UUID accountId = UUID.fromString(accountIdResult.get().value());
                offset = accountIdResult.get().end();

                Optional<ParseResult<String>> wsAfterAccountId = parseWhitespace(source, offset);
                if (wsAfterAccountId.isPresent()) {
                    offset = wsAfterAccountId.get().end();
                }

                Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, offset);
                if (amountResult.isEmpty()) {
                    yield Optional.empty();
                }
                BigDecimal amount = amountResult.get().value();
                offset = amountResult.get().end();

                yield Optional.of(new ParseResult<>(new Withdraw(accountId, amount), start, offset));
            }

            case "transfer" -> {
                // transfer <source_id> <target_id> <amount>
                Optional<ParseResult<String>> wsAfterTransfer = parseWhitespace(source, offset);
                if (wsAfterTransfer.isEmpty()) {
                    yield Optional.empty();
                }
                offset = wsAfterTransfer.get().end();

                Optional<ParseResult<String>> sourceIdResult = parseAccountId(source, offset);
                if (sourceIdResult.isEmpty()) {
                    yield Optional.empty();
                }
                UUID sourceId = UUID.fromString(sourceIdResult.get().value());
                offset = sourceIdResult.get().end();

                Optional<ParseResult<String>> wsAfterSourceId = parseWhitespace(source, offset);
                if (wsAfterSourceId.isPresent()) {
                    offset = wsAfterSourceId.get().end();
                }

                Optional<ParseResult<String>> targetIdResult = parseAccountId(source, offset);
                if (targetIdResult.isEmpty()) {
                    yield Optional.empty();
                }
                UUID targetId = UUID.fromString(targetIdResult.get().value());
                offset = targetIdResult.get().end();

                Optional<ParseResult<String>> wsAfterTargetId = parseWhitespace(source, offset);
                if (wsAfterTargetId.isPresent()) {
                    offset = wsAfterTargetId.get().end();
                }

                Optional<ParseResult<BigDecimal>> amountResult = parseAmount(source, offset);
                if (amountResult.isEmpty()) {
                    yield Optional.empty();
                }
                BigDecimal amount = amountResult.get().value();
                offset = amountResult.get().end();

                yield Optional.of(new ParseResult<>(new Transfer(sourceId, targetId, amount), start, offset));
            }

            case "balance" -> {
                // balance <account_id>
                Optional<ParseResult<String>> wsAfterBalance = parseWhitespace(source, offset);
                if (wsAfterBalance.isEmpty()) {
                    yield Optional.empty();
                }
                offset = wsAfterBalance.get().end();

                Optional<ParseResult<String>> accountIdResult = parseAccountId(source, offset);
                if (accountIdResult.isEmpty()) {
                    yield Optional.empty();
                }
                UUID accountId = UUID.fromString(accountIdResult.get().value());
                offset = accountIdResult.get().end();

                Optional<ParseResult<String>> wsAfterAccountId = parseWhitespace(source, offset);
                if (wsAfterAccountId.isPresent()) {
                    offset = wsAfterAccountId.get().end();
                }

                yield Optional.of(new ParseResult<>(new Balance(accountId), start, offset));
            }

            case "list-accounts" -> {
                // list-accounts - без аргументов
                Optional<ParseResult<String>> wsAfterList = parseWhitespace(source, offset);
                if (wsAfterList.isPresent()) {
                    offset = wsAfterList.get().end();
                }

                yield Optional.of(new ParseResult<>(new ListAccounts(), start, offset));
            }

            default -> Optional.empty(); // неизвестная команда
        };
    }
    
    public static List<Command> parseCommandsFromString(String source) {
        List<Command> commandsList = new ArrayList<>();
        int start = 0;
        
        while (start < source.length()) {
            Optional<ParseResult<Command>> commandOpt = parseCommand(source, start);
            if (commandOpt.isPresent()) {
                commandsList.add(commandOpt.get().value());
                start = commandOpt.get().end();
            } else {
                break;
            }
        }
        return commandsList;
    }

}