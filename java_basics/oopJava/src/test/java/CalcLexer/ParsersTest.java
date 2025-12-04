package CalcLexer;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class ParsersTest {

    //region parseSign
    @Test(groups = "sign", description = "Парсинг знака плюс: '+'")
    public void testParseSignPlus() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign(Optional.of("+"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Boolean> parsed = result.get();
        assertEquals(parsed.value(), true);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "sign", description = "Парсинг знака минус: '-'")
    public void testParseSignMinus() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign(Optional.of("-"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Boolean> parsed = result.get();
        assertEquals(parsed.value(), false);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "sign", description = "Парсинг знака: пустая строка — ожидается пустой результат")
    public void testParseSignEmpty() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign(Optional.of(""), 0);
        Assert.assertFalse(result.isPresent());
    }
    //endregion

    //region parseInt
    @Test(groups = "int", description = "Парсинг целого числа: 123")
    public void testParseIntPositive() {
        Optional<ParseResult<Integer>> result = Parsers.parseInt(Optional.of("123"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Integer> parsed = result.get();
        assertEquals(parsed.value(), 123);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 3);
    }

    @Test(groups = "int", description = "Парсинг целого числа: -456")
    public void testParseIntNegative() {
        Optional<ParseResult<Integer>> result = Parsers.parseInt(Optional.of("-456"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Integer> parsed = result.get();
        assertEquals(parsed.value(), -456);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 4);
    }

    @Test(groups = "int", description = "Парсинг целого числа: 0")
    public void testParseIntZero() {
        Optional<ParseResult<Integer>> result = Parsers.parseInt(Optional.of("0"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Integer> parsed = result.get();
        assertEquals(parsed.value(), 0);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }
    //endregion

    //region parseBrackets
    @Test(groups = "brackets", description = "Парсинг открывающей скобки: '('")
    public void testParseBracketsOpening() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets(Optional.of("("), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Brackets.OPENING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг закрывающей скобки: ')' ")
    public void testParseBracketsClosing() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets(Optional.of(")"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Brackets.CLOSING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг скобки: символ не скобка — ожидается пустой результат")
    public void testParseBracketsInvalid() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets(Optional.of("a"), 0);
        Assert.assertFalse(result.isPresent());
    }
    //endregion

    //region parseOperation
    @Test(groups = "operation", description = "Парсинг операции: '+'")
    public void testParseOperationAdd() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation(Optional.of("+"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.ADD);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '*'")
    public void testParseOperationMul() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation(Optional.of("*"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.MUL);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '/'")
    public void testParseOperationDiv() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation(Optional.of("/"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.DIV);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }
    //endregion

    //region parseWhitespace
    @Test(groups = "whitespace", description = "Парсинг пробелов: '    ' (4 пробела)")
    public void testParseWhitespaceSpaces() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace(Optional.of("    hello"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 4);
    }

    @Test(groups = "whitespace", description = "Парсинг табуляции: '\\t'")
    public void testParseWhitespaceTab() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace(Optional.of("\thello"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсинг пробелов и табуляции: '  \t  ' (2 пробела, 1 таб, 2 пробела)")
    public void testParseWhitespaceMixed() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace(Optional.of("  \t  hello"), 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 5);
    }
    //endregion
}