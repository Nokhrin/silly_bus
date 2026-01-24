package command.parser;

import command.dto.*;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Проверка парсинга операций.
 * Задача тестов - проверить что команда и параметры прочитаны корректно,
 * смещение курсора выполнено корректно
 */
public class ParserTest {

    //region parseWhitespace

    @Test(description = "parseWhitespace - пробелы удалены, финальный сдвиг - на первом символе слова")
    public void testParseWhitespace_ValidWhitespace() {
        String input = "   fin-calc";
        Optional<ParseResult<String>> result = Parser.parseWhitespace(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 3);
    }

    @Test(description = "parseWhitespace - пробелы удалены, финальный сдвиг == индекс последнего пробела + 1")
    public void testParseWhitespace_OnlySpaces() {
        String input = " \t\n\r ";
        Optional<ParseResult<String>> result = Parser.parseWhitespace(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 5);
    }

    //endregion parseWhitespace


    //region parseAccountId
    @Test(description = "parseAccountId - считан валидный UUID в нижнем регистре")
    public void testParseAccountId_ValidUuid() {
        String input = "550e8400-e29b-41d4-a716-446655440000";
        Optional<ParseResult<String>> result = Parser.parseAccountId(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "550e8400-e29b-41d4-a716-446655440000");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 36);
    }

    @Test(description = "parseAccountId - считан валидный UUID в верхнем регистре")
    public void testParseAccountId_ValidUuidWithUpperCase() {
        String input = "A1B2C3D4-E5F6-41D4-A716-446655440000 account";
        Optional<ParseResult<String>> result = Parser.parseAccountId(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "A1B2C3D4-E5F6-41D4-A716-446655440000");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 36);
    }
    //endregion parseAccountId
    
    
    //region parseAmount
    @Test(description = "Сумма - положительная, дробная, корректная, в строке символы только суммы, считана")
    public void testParseAmount_FloatPos() {
        String input = "123.45";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("123.45"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 6);
    }

    @Test(description = "Сумма - положительная, целая, корректная, в строке символы только суммы, считана")
    public void testParseAmount_IntegerPos() {
        String input = "123";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("123"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 3);
    }

    @Test(description = "Сумма - положительная, целая, корректная, в строке символы только суммы, считана")
    public void testParseAmount_IntegerNeg() {
        String input = "123";
        Optional<ParseResult<BigDecimal>> result = Parser.parseAmount(input, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new BigDecimal("123"));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 3);
    }

    //endregion parseAmount
    
    
    //region parseCommand open
    @Test(description = "Парсинг команды open без пробелов")
    public void testParseCommand_Open() {
        String input = "open";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof OpenAccountData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг команды open с пробелами")
    public void testParseCommand_OpenWithWhitespace() {
        String input = "   open   ";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof OpenAccountData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 10);
    }
    //endregion parseCommand open


    //region parseCommand deposit
    @Test(description = "Парсинг deposit с корректным синтаксисом")
    public void testParseCommand_Deposit() {
        String input = "deposit 550e8400-e29b-41d4-a716-446655440000 123.45";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof DepositData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 51);
    }
    //endregion parseCommand deposit


    //region parseCommand withdraw
    @Test(description = "Парсинг withdraw с корректным синтаксисом")
    public void testParseCommand_Withdraw() {
        String input = "withdraw 550e8400-e29b-41d4-a716-446655440000 123.45";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof WithdrawData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 52);
    }
    //endregion parseCommand withdraw


    //region parseCommand transfer
    @Test(description = "Парсинг transfer с корректным синтаксисом")
    public void testParseCommand_Transfer() {
        String input = "transfer 550e8400-e29b-41d4-a716-446655440000 12345678-1234-41D4-A716-446655440000 123.45";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);
        
        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof TransferData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 89);
    }
    //endregion parseCommand transfer

    //region parseCommand balance
    @Test(description = "Парсинг balance с корректным синтаксисом")
    public void testParseCommand_Balance() {
        String input = "balance 550e8400-e29b-41d4-a716-446655440000";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof BalanceData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 44);
    }
    //endregion parseCommand balance
    
    //region parseCommand list
    @Test(description = "Парсинг list с корректным синтаксисом")
    public void testParseCommand_List() {
        String input = "list";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof ListAccountsData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }
    //endregion parseCommand list
    
    //region parseCommand close
    @Test(description = "Парсинг close с корректным синтаксисом")
    public void testParseCommand_Close() {
        String input = "close 550e8400-e29b-41d4-a716-446655440000";
        Optional<ParseResult<CommandData>> result = Parser.parseCommand(input, 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value() instanceof CloseAccountData);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 42);
    }
    //endregion parseCommand close

    //region parseCommand последовательности команд
    @Test(description = "Строка с двумя командами интерпретированы как список из двух команд")
    public void testParseCommandsFromString_OpenAndOtherCommand() {
        String input = "open close 550e8400-e29b-41d4-a716-446655440000";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 2);
        assertTrue(commandDataList.get(0) instanceof OpenAccountData);
        assertTrue(commandDataList.get(1) instanceof CloseAccountData);
    }

    @Test(description = "Строка с тремя командами интерпретированы как список из трех команд")
    public void testParseCommandsFromString_ThreeCommands() {
        String input = "open deposit 550e8400-e29b-41d4-a716-446655440000 100.00 withdraw 550e8400-e29b-41d4-a716-446655440000 50.00";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 3);
        assertTrue(commandDataList.get(0) instanceof OpenAccountData);
        assertTrue(commandDataList.get(1) instanceof DepositData);
        assertTrue(commandDataList.get(2) instanceof WithdrawData);
    }

    @Test(description = "Строка с командами разных типов и пробелами")
    public void testParseCommandsFromString_MixedCommands() {
        String input = "   open   \n\t deposit 550e8400-e29b-41d4-a716-446655440000 123.45   \t\n  withdraw 550e8400-e29b-41d4-a716-446655440000 67.89   ";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 3);
        assertTrue(commandDataList.get(0) instanceof OpenAccountData);
        assertTrue(commandDataList.get(1) instanceof DepositData);
        assertTrue(commandDataList.get(2) instanceof WithdrawData);
    }

    @Test(description = "Строка с одной командой")
    public void testParseCommandsFromString_SingleCommand() {
        String input = "deposit 550e8400-e29b-41d4-a716-446655440000 123.45";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 1);
        assertTrue(commandDataList.get(0) instanceof DepositData);
    }

    @Test(description = "Строка с пустыми командами и пробелами")
    public void testParseCommandsFromString_EmptyAndWhitespace() {
        String input = "   \n\t  ";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 0);
    }

    @Test(description = "Строка с неправильной командой - парсинг останавливается")
    public void testParseCommandsFromString_InvalidCommandStopsParsing() {
        String input = "open invalid_command 550e8400-e29b-41d4-a716-446655440000 deposit 550e8400-e29b-41d4-a716-446655440000 100.00";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        // Только первая команда должна быть распарсена
        assertEquals(commandDataList.size(), 1);
        assertTrue(commandDataList.get(0) instanceof OpenAccountData);
    }

    @Test(description = "Строка с командами, где одна невалидна - остальные парсятся")
    public void testParseCommandsFromString_MixedValidInvalidCommands() {
        String input = "open deposit 550e8400-e29b-41d4-a716-446655440000 100.00 invalid_command 550e8400-e29b-41d4-a716-446655440000";
        List<CommandData> commandDataList = Parser.parseCommandsFromString(input);

        assertEquals(commandDataList.size(), 2);
        assertTrue(commandDataList.get(0) instanceof OpenAccountData);
        assertTrue(commandDataList.get(1) instanceof DepositData);
    }
    //endregion parseCommand последовательности команд
}
