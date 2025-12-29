package command.parser;

import org.testng.annotations.Test;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

@Test
public class ParserTest {

    //region Constants

    /**
     * Набор пробельных символов: пробел, табуляция, возврат каретки, новая строка.
     */
    private static final Set<Character> WHITESPACES = Set.of(' ', '\t', '\n', '\r');

    /**
     * Регулярное выражение для валидации формата UUID.
     */
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[a-f\\d]{8}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{12}$", Pattern.CASE_INSENSITIVE
    );

    //endregion

    //region parseWhitespace

    /**
     * Тестирование парсинга пробельных символов.
     */
    @Test
    public void testParseWhitespace() {
        // Позитивные случаи
        Optional<ParseResult<String>> result1 = Parser.parseWhitespace("   ", 0);
        assertTrue(result1.isPresent());
        assertEquals(result1.get().value(), "");
        assertEquals(result1.get().start(), 0);
        assertEquals(result1.get().end(), 3);

        Optional<ParseResult<String>> result2 = Parser.parseWhitespace("\t\n\r", 0);
        assertTrue(result2.isPresent());
        assertEquals(result2.get().value(), "");
        assertEquals(result2.get().start(), 0);
        assertEquals(result2.get().end(), 3);

        Optional<ParseResult<String>> result3 = Parser.parseWhitespace(" \t\n", 0);
        assertTrue(result3.isPresent());
        assertEquals(result3.get().value(), "");
        assertEquals(result3.get().start(), 0);
        assertEquals(result3.get().end(), 3);

        // Негативные случаи
        Optional<ParseResult<String>> result4 = Parser.parseWhitespace("", 0);
        assertFalse(result4.isPresent());

        Optional<ParseResult<String>> result5 = Parser.parseWhitespace("abc", 0);
        assertFalse(result5.isPresent());

        Optional<ParseResult<String>> result6 = Parser.parseWhitespace("a\tb", 0);
        assertFalse(result6.isPresent());

        Optional<ParseResult<String>> result7 = Parser.parseWhitespace(" \t", 0);
        assertTrue(result7.isPresent());
        assertEquals(result7.get().end(), 2);

        // Проверка пустого результата
        Optional<ParseResult<String>> result8 = Parser.parseWhitespace("notWhitespace", 0);
        assertFalse(result8.isPresent());
    }

    //endregion


    //region parseAccountId

    @Test
    public void testParseAccountId_ValidUUID_Lowercase() {
        // Валидный UUID в нижнем регистре
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1b2c3d4-e5f6-7890-1234-56789abcdef0", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "a1b2c3d4-e5f6-7890-1234-56789abcdef0");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 36);
    }

    @Test
    public void testParseAccountId_ValidUUID_Uppercase() {
        // Валидный UUID в верхнем регистре
        Optional<ParseResult<String>> result = Parser.parseAccountId("A1B2C3D4-E5F6-7890-1234-56789ABCDEF0", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "A1B2C3D4-E5F6-7890-1234-56789ABCDEF0");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 36);
    }

    @Test
    public void testParseAccountId_ValidUUID_MixedCase() {
        // Валидный UUID с чередованием регистра
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1B2c3D4-e5F6-7890-1234-56789AbcDeF0", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "a1B2c3D4-e5F6-7890-1234-56789AbcDeF0");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 36);
    }

    @Test
    public void testParseAccountId_InvalidChar_G_InMiddle() {
        // `g` — не шестнадцатеричная цифра
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8", 0);
        assertFalse(result.isPresent(), "Should not parse UUID with 'g' in it");
    }

    @Test
    public void testParseAccountId_InvalidChar_Z_AtEnd() {
        // `z` — не входит в `a-fA-F0-9`
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8z", 0);
        assertFalse(result.isPresent(), "Should not parse UUID with 'z' at the end");
    }

    @Test
    public void testParseAccountId_InvalidChar_ExclamationMark() {
        // `!` — не входит в `a-fA-F0-9`
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8!", 0);
        assertFalse(result.isPresent(), "Should not parse UUID with '!' in it");
    }

    @Test
    public void testParseAccountId_EmptyString() {
        // Пустая строка — не валидна
        Optional<ParseResult<String>> result = Parser.parseAccountId("", 0);
        assertFalse(result.isPresent(), "Should not parse empty string");
    }

    @Test
    public void testParseAccountId_InvalidChar_M_N() {
        // `m` и `n` — не входят в `a-fA-F0-9`
        Optional<ParseResult<String>> result = Parser.parseAccountId("a1b2c3d4-e5f6-7890-1234-56789abcdeMn", 0);
        assertFalse(result.isPresent(), "Should not parse UUID with 'm' and 'n' in it");
    }

    //endregion

    //region UUID_PATTERN

    /**
     * Тестирование регулярного выражения UUID_PATTERN.
     */
    @Test
    public void testUUID_PATTERN() {
        assertTrue(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-1234-56789abcdef0").matches());
        assertTrue(UUID_PATTERN.matcher("f47ac10b-58a1-4344-b672-e6a653f3e4da").matches());
        assertTrue(UUID_PATTERN.matcher("A1B2C3D4-E5F6-7890-1234-56789ABCDEF0").matches());
        assertTrue(UUID_PATTERN.matcher("12345678-1234-1234-1234-123456789abc").matches());

        assertFalse(UUID_PATTERN.matcher("123").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8z").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8!").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8 ").matches());
        assertFalse(UUID_PATTERN.matcher("").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8-").matches());
    }
    //endregion


    //region testParseAmount
    @Test
    public void testParseAmount_NegativeInteger() {
        // given
        String input = "-456";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("-456"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test
    public void testParseAmount_PositiveDecimal() {
        // given
        String input = "123.45";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("123.45"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 6);
    }

    @Test
    public void testParseAmount_NegativeDecimal() {
        // given
        String input = "-123.45";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("-123.45"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 7);
    }

    @Test
    public void testParseAmount_DecimalWithoutIntegerPart() {
        // given
        String input = ".45";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("0.45"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 3);
    }

    @Test
    public void testParseAmount_NegativeDecimalWithoutIntegerPart() {
        // given
        String input = "-.45";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("-0.45"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test
    public void testParseAmount_InvalidFormat_OnlyDot() {
        // given
        String input = ".";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testParseAmount_InvalidFormat_ExtraCharacters() {
        // given
        String input = "123abc";
        int start = 0;

        // when
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, start);

        // then
        assertTrue(result.isPresent());
        // Проверим, что парсинг остановился на 3-м символе (до "abc")
        assertEquals(result.get().value(), new BigDecimal("123"));
        assertEquals(result.get().end(), 3);
    }
    //endregion

    
    //region ParserCommandTest
    @Test
    public void testParseCommand_Open() {
        // given
        String input = "open";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Open);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test
    public void testParseCommand_Open_WithTrailingWhitespace() {
        // given
        String input = "open   ";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Open);
        assertEquals(result.get().end(), 7); // "open   " — пробелы после
    }

    @Test
    public void testParseCommand_Close_ValidId() {
        // given
        String input = "close 1a2b3c4d-5e6f-7890-1234-567890abcdef";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Close);
        Close close = (Close) result.get().value();
        assertEquals(close.accountId().toString(), "1a2b3c4d-5e6f-7890-1234-567890abcdef");
        assertEquals(result.get().end(), 42); // длина строки
    }

    @Test
    public void testParseCommand_Deposit_ValidIdAndAmount() {
        // given
        String input = "deposit 1a2b3c4d-5e6f-7890-1234-567890abcdef 123.45";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Deposit);
        Deposit deposit = (Deposit) result.get().value();
        assertEquals(deposit.accountId().toString(), "1a2b3c4d-5e6f-7890-1234-567890abcdef");
        assertEquals(deposit.amount(), new BigDecimal("123.45"));
        assertEquals(result.get().end(), 51);
    }

    @Test
    public void testParseCommand_Withdraw_NegativeAmount() {
        // given
        String input = "withdraw 1a2b3c4d-5e6f-7890-1234-567890abcdef -50.25";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Withdraw);
        Withdraw withdraw = (Withdraw) result.get().value();
        assertEquals(withdraw.accountId().toString(), "1a2b3c4d-5e6f-7890-1234-567890abcdef");
        assertEquals(withdraw.amount(), new BigDecimal("-50.25"));
        assertEquals(result.get().end(), 52);
    }

    @Test
    public void testParseCommand_Transfer_ValidIdsAndAmount() {
        // given
        String input = "transfer 1a2b3c4d-5e6f-7890-1234-567890abcdef b2c073c6-3ae4-4333-a189-a6388774aaa1 -100.00";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Transfer);
        Transfer transfer = (Transfer) result.get().value();
        assertEquals(transfer.sourceId().toString(), "1a2b3c4d-5e6f-7890-1234-567890abcdef");
        assertEquals(transfer.targetId().toString(), "b2c073c6-3ae4-4333-a189-a6388774aaa1");
        assertEquals(transfer.amount(), new BigDecimal("-100.00"));
        assertEquals(result.get().end(), 90);
    }

    @Test
    public void testParseCommand_Balance_ValidId() {
        // given
        String input = "balance 1a2b3c4d-5e6f-7890-1234-567890abcdef";
        int start = 0;

        // when
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, start);

        // then
    }
    //endregion
    
    //region parseCommandsFromString
    @Test
    public void testParseSingleOpenCommand() {
        String input = "open";
        List<Command> commands = Parser.parseCommandsFromString(input);

        assertEquals(commands.size(), 1);
        assertTrue(commands.get(0) instanceof Open);
    }

    @Test
    public void testParseMultipleCommands() {
        String input = "open deposit 123e4567-e89b-12d3-a456-426614174000 100.00 withdraw 123e4567-e89b-12d3-a456-426614174000 50.00";
        List<Command> commands = Parser.parseCommandsFromString(input);

        assertEquals(commands.size(), 3);
        assertTrue(commands.get(0) instanceof Open);
        assertTrue(commands.get(1) instanceof Deposit);
        assertTrue(commands.get(2) instanceof Withdraw);

        Deposit depositCmd = (Deposit) commands.get(1);
        assertEquals(depositCmd.accountId(), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertEquals(depositCmd.amount(), new BigDecimal("100.00"));

        Withdraw withdrawCmd = (Withdraw) commands.get(2);
        assertEquals(withdrawCmd.accountId(), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertEquals(withdrawCmd.amount(), new BigDecimal("50.00"));
    }

    @Test
    public void testParseTransferCommand() {
        String input = "transfer 123e4567-e89b-12d3-a456-426614174000 87654321-e89b-12d3-a456-426614174000 250.50";
        List<Command> commands = Parser.parseCommandsFromString(input);

        assertEquals(commands.size(), 1);
        assertTrue(commands.get(0) instanceof Transfer);

        Transfer transferCmd = (Transfer) commands.get(0);
        assertEquals(transferCmd.sourceId(), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertEquals(transferCmd.targetId(), UUID.fromString("87654321-e89b-12d3-a456-426614174000"));
        assertEquals(transferCmd.amount(), new BigDecimal("250.50"));
    }

    @Test
    public void testParseCommandsWithWhitespace() {
        String input = "  open   \t\n  deposit 123e4567-e89b-12d3-a456-426614174000 100.00  ";
        List<Command> commands = Parser.parseCommandsFromString(input);

        assertEquals(commands.size(), 2);
        assertTrue(commands.get(0) instanceof Open);
        assertTrue(commands.get(1) instanceof Deposit);

        Deposit depositCmd = (Deposit) commands.get(1);
        assertEquals(depositCmd.accountId(), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertEquals(depositCmd.amount(), new BigDecimal("100.00"));
    }
    //endregion
}