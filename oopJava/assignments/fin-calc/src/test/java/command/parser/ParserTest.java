package command.parser;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.testng.Assert.*;
public class ParserTest {

    //region parseWhitespace

    @Test(description = "parseWhitespace - пробелы удалены, финальный сдвиг - на первом символе слова")
    public void testParseWhitespace_ValidWhitespace() {
        String input = "   fin-calc";
        Optional<ParseResult<String>> result = Parser.parseWhitespace(input, 0);

        assertTrue(result.isPresent());
        assertEquals("", result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(3, result.get().end());
    }

    @Test(description = "parseWhitespace - пробелы удалены, финальный сдвиг == индекс последнего пробела + 1")
    public void testParseWhitespace_OnlySpaces() {
        String input = " \t\n\r ";
        Optional<ParseResult<String>> result = Parser.parseWhitespace(input, 0);

        assertTrue(result.isPresent());
        assertEquals("", result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(5, result.get().end());
    }

    //endregion parseWhitespace


    //region parseAccountId
    @Test(description = "parseAccountId - считан валидный UUID в нижнем регистре")
    public void testParseAccountId_ValidUuid() {
        String input = "550e8400-e29b-41d4-a716-446655440000";
        Optional<ParseResult<String>> result = Parser.parseAccountId(input, 0);

        assertTrue(result.isPresent());
        assertEquals("550e8400-e29b-41d4-a716-446655440000", result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(36, result.get().end());
    }

    @Test(description = "parseAccountId - считан валидный UUID в верхнем регистре")
    public void testParseAccountId_ValidUuidWithUpperCase() {
        String input = "A1B2C3D4-E5F6-41D4-A716-446655440000 account";
        Optional<ParseResult<String>> result = Parser.parseAccountId(input, 0);

        assertTrue(result.isPresent());
        assertEquals("A1B2C3D4-E5F6-41D4-A716-446655440000", result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(36, result.get().end());
    }
    //endregion parseAccountId
    
    
    //region parseAmount
    @Test(description = "Сумма - положительная, дробная, корректная, в строке символы только суммы, считана")
    public void testParseAmount_FloatPos() {
        String input = "123.45";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("123.45"), result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(6, result.get().end());
    }

    @Test(description = "Сумма - отрицательная, дробная, корректная, в строке символы только суммы, считана")
    public void testParseAmount_FloatNeg() {
        String input = "-987.65";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("-987.65"), result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(7, result.get().end());
    }

    @Test(description = "Сумма - положительная, целая, корректная, в строке символы только суммы, считана")
    public void testParseAmount_IntegerPos() {
        String input = "123";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("123"), result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(3, result.get().end());
    }

    @Test(description = "Сумма - положительная, целая, корректная, в строке символы только суммы, считана")
    public void testParseAmount_IntegerNeg() {
        String input = "123";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("123"), result.get().value());
        assertEquals(0, result.get().start());
        assertEquals(3, result.get().end());
    }

    //endregion parseAmount
    
    
    //region parseCommand open
    @Test(description = "Парсинг команды open без пробелов")
    public void testParseCommand_Open() {
        String input = "open";
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Open);
        assertEquals(0, result.get().start());
        assertEquals(4, result.get().end());
    }

    @Test(description = "Парсинг команды open с пробелами")
    public void testParseCommand_OpenWithWhitespace() {
        String input = "   open   ";
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Open);
        assertEquals(0, result.get().start());
        assertEquals(10, result.get().end());
    }
    //endregion parseCommand open


    //region parseCommand deposit
    @Test(description = "Парсинг deposit с корректным синтаксисом")
    public void testParseCommand_Deposit() {
        String input = "deposit 550e8400-e29b-41d4-a716-446655440000 123.45";
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Deposit);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), input.length());

        Deposit cmd = (Deposit) result.get().value();
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), cmd.accountId());
        assertEquals(new BigDecimal("123.45"), cmd.amount());
    }
    //endregion parseCommand deposit


    //region parseCommand withdraw
    @Test(description = "Парсинг withdraw с корректным синтаксисом")
    public void testParseCommand_Withdraw() {
        String input = "withdraw 550e8400-e29b-41d4-a716-446655440000 123.45";
        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof Withdraw);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), input.length());

        Withdraw cmd = (Withdraw) result.get().value();
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), cmd.accountId());
        assertEquals(new BigDecimal("123.45"), cmd.amount());
    }
    //endregion parseCommand withdraw


    //region parseCommand transfer
//    @Test(description = "Парсинг transfer с корректным синтаксисом")
//    public void testParseCommand_Transfer() {
//        String input = "transfer 550e8400-e29b-41d4-a716-446655440000 12345678-1234-41D4-A716-446655440000 123.45";
//        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);
//
//    }
    //endregion parseCommand transfer

    //region parseCommand balance
//    @Test(description = "Парсинг balance с корректным синтаксисом")
//    public void testParseCommand_Balance() {
//        String input = "balance 550e8400-e29b-41d4-a716-446655440000";
//        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);
//
//    }
    //endregion parseCommand balance
    
    //region parseCommand list
//    @Test(description = "Парсинг list с корректным синтаксисом")
//    public void testParseCommand_List() {
//        String input = "list";
//        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);
//
//
//    }
    //endregion parseCommand list
    
    //region parseCommand close
//    @Test(description = "Парсинг close с корректным синтаксисом")
//    public void testParseCommand_Close() {
//        String input = "close 550e8400-e29b-41d4-a716-446655440000";
//        Optional<ParseResult<Command>> result = Parser.parseCommand(input, 0);
//
//
//    }
    //endregion parseCommand close

    //region parseCommand последовательности команд
    @Test(description = "Строка с двумя командами интерпретированы как список из двух команд")
    public void testParseCommandsFromString_OpenAndOtherCommand() {
        String input = "open close 550e8400-e29b-41d4-a716-446655440000";
        List<Command> commands = Parser.parseCommandsFromString(input);

        assertEquals(2, commands.size());
        assertTrue(commands.get(0) instanceof Open);
        assertTrue(commands.get(1) instanceof Close);
    }
    //endregion parseCommand последовательности команд
}