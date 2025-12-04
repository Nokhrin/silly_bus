package CalcLexer;
import static CalcLexer.Parsers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import static CalcLexer.Parsers.parseMulDivExpression;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ParsersTest {

    
    @Test(groups = "sign", description = "Парсинг знака плюс: '+'")
    public void testParseSignPlus() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign("+", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Boolean> parsed = result.get();
        assertEquals(parsed.value(), true);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "sign", description = "Парсинг знака минус: '-'")
    public void testParseSignMinus() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign("-", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Boolean> parsed = result.get();
        assertEquals(parsed.value(), false);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "sign", description = "Парсинг знака: пустая строка - ожидается пустой результат")
    public void testParseSignEmpty() {
        Optional<ParseResult<Boolean>> result = Parsers.parseSign("", 0);
        Assert.assertFalse(result.isPresent());
    }
    //endregion

    
    @Test(groups = "int", description = "Парсинг целого числа: 123")
    public void testParseNumberPositive() {
        Optional<ParseResult<NumValue>> result = Parsers.parseNumber("123", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<NumValue> parsed = result.get();
        assertEquals(parsed.value(), new NumValue(123));
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 3);
    }

    @Test(groups = "int", description = "Парсинг целого числа: -456")
    public void testParseNumberNegative() {
        Optional<ParseResult<NumValue>> result = Parsers.parseNumber("-456", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<NumValue> parsed = result.get();
        assertEquals(parsed.value(), new NumValue(-456));
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 4);
    }

    @Test(groups = "int", description = "Парсинг целого числа: 0")
    public void testParseNumberZero() {
        Optional<ParseResult<NumValue>> result = Parsers.parseNumber("0", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<NumValue> parsed = result.get();
        assertEquals(parsed.value(), new NumValue(0));
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }
    //endregion

    
    @Test(groups = "brackets", description = "Парсинг открывающей скобки: '('")
    public void testParseBracketsOpening() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets("(", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Brackets.OPENING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг закрывающей скобки: ')' ")
    public void testParseBracketsClosing() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets(")", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Brackets.CLOSING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг скобки: символ не скобка - ожидается пустой результат")
    public void testParseBracketsInvalid() {
        Optional<ParseResult<Parsers.Brackets>> result = Parsers.parseBrackets("a", 0);
        Assert.assertFalse(result.isPresent());
    }
    //endregion

    
    @Test(groups = "operation", description = "Парсинг операции: '+'")
    public void testParseOperationAdd() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation("+", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.ADD);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '*'")
    public void testParseOperationMul() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation("*", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.MUL);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '/'")
    public void testParseOperationDiv() {
        Optional<ParseResult<Parsers.Operation>> result = Parsers.parseOperation("/", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parsers.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parsers.Operation.DIV);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }
    //endregion

    
    @Test(groups = "whitespace", description = "Парсинг пробелов: '    ' (4 пробела)")
    public void testParseWhitespaceSpaces() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace("    hello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 4);
    }

    @Test(groups = "whitespace", description = "Парсинг табуляции: '\\t'")
    public void testParseWhitespaceTab() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace("\thello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсинг пробелов и табуляции: '  \t  ' (2 пробела, 1 таб, 2 пробела)")
    public void testParseWhitespaceMixed() {
        Optional<ParseResult<String>> result = Parsers.parseWhitespace("  \t  hello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 5);
    }
    //endregion

    
    @Test(groups = "success", description = "Парсинг простого выражения: 5 * 3")
    public void testParseMulDivExpression_MULSimple() {
        String source = "5*3";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());
        assertEquals(((BinaryExpression) result.get().value()).left(), new NumValue(5));
        assertEquals(((BinaryExpression) result.get().value()).op(), Operation.MUL);
        assertEquals(((BinaryExpression) result.get().value()).right(), new NumValue(3));
    }

    @Test(groups = "success", description = "Парсинг выражения с пробелами: 5 * 3 / 2")
    public void testParseMulDivExpression_WithWhitespace() {
        String source = "5 * 3 / 2";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(5));
        assertEquals(expr.op(), Operation.MUL);

        BinaryExpression rightExpr = (BinaryExpression) expr.right();
        assertEquals(rightExpr.left(), new NumValue(3));
        assertEquals(rightExpr.op(), Operation.DIV);
        assertEquals(rightExpr.right(), new NumValue(2));
    }

    @Test(groups = "success", description = "Парсинг цепочки из трёх операций: 10 / 2 * 5")
    public void testParseMulDivExpression_Chained() {
        String source = "10/2*5";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(10));
        assertEquals(expr.op(), Operation.DIV);

        BinaryExpression right = (BinaryExpression) expr.right();
        assertEquals(right.left(), new NumValue(2));
        assertEquals(right.op(), Operation.MUL);
        assertEquals(right.right(), new NumValue(5));
    }

    @Test(groups = "success", description = "Парсинг одного числа - возвращает его как выражение")
    public void testParseMulDivExpression_SingleNumber() {
        String source = "42";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());
        assertEquals(result.get().value(), new NumValue(42));
    }
    //endregion

    
    @Test(groups = "error", description = "Парсинг с нечисловым значением после оператора")
    public void testParseMulDivExpression_InvalidNumberAfterOperator() {
        String source = "5*abc";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с пустым источником")
    public void testParseMulDivExpression_EmptySource() {
        String source = "";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с неизвестным оператором в начале")
    public void testParseMulDivExpression_InvalidOperatorAtStart() {
        String source = "+5*3";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с оператором в конце")
    public void testParseMulDivExpression_OperatorAtEnd() {
        String source = "5*";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isEmpty());
    }
    //endregion

    
    @Test(groups = "edge", description = "Парсинг с нулём: 0 / 1")
    public void testParseMulDivExpression_ZeroDivision() {
        String source = "0/1";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(0));
        assertEquals(expr.op(), Operation.DIV);
        assertEquals(expr.right(), new NumValue(1));
    }

    @Test(groups = "edge", description = "Парсинг с отрицательным числом (только в контексте операции)")
    public void testParseMulDivExpression_NegativeNumber() {
        // Обрати внимание: в текущей реализации parseMulDivExpression ожидает только положительные числа
        // Так что парсинг "-5" сюда не пройдёт - он не является числом в текущей логике
        String source = "-5*3";
        Optional<ParseResult<Expression>> result = parseMulDivExpression(source, 0);

        assertTrue(result.isEmpty());
    }
    //endregion
}