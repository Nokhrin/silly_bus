package CalcLexer;

import org.testng.annotations.Test;

import java.util.Optional;
import static CalcLexer.Parsers.*;
import static CalcLexer.NaryExpression.parseNaryExpression;
import static org.testng.Assert.*;

public class NaryExpressionTest {
    //region
    @Test(groups = "success", description = "Парсинг простого выражения: 5 + 3")
    public void testParseNaryExpression_AddSimple() {
        String source = "5+3";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(5));
        assertEquals(expr.op(), Parsers.Operation.ADD);
        assertEquals(expr.right(), new NumValue(3));
    }

    @Test(groups = "success", description = "Парсинг выражения с пробелами: 5 + 3 - 2")
    public void testParseNaryExpression_WithWhitespace() {
        String source = "5 + 3 - 2";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(5));
        assertEquals(expr.op(), Operation.ADD);

        BinaryExpression right = (BinaryExpression) expr.right();
        assertEquals(right.left(), new NumValue(3));
        assertEquals(right.op(), Operation.SUB);
        assertEquals(right.right(), new NumValue(2));
    }

    @Test(groups = "success", description = "Парсинг цепочки из трёх операций: 10 - 2 + 5")
    public void testParseNaryExpression_Chained() {
        String source = "10-2+5";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(10));
        assertEquals(expr.op(), Operation.SUB);

        BinaryExpression right = (BinaryExpression) expr.right();
        assertEquals(right.left(), new NumValue(2));
        assertEquals(right.op(), Operation.ADD);
        assertEquals(right.right(), new NumValue(5));
    }

    @Test(groups = "success", description = "Парсинг одного выражения (без операторов) - возвращает его как выражение")
    public void testParseNaryExpression_SingleExpression() {
        String source = "42";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());
        assertEquals(result.get().value(), new NumValue(42));
    }

    @Test(groups = "success", description = "Парсинг выражения с умножением внутри: 5 + 3 * 2")
    public void testParseNaryExpression_WithMulDivInside() {
        String source = "5+3*2";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(5));
        assertEquals(expr.op(), Operation.ADD);

        BinaryExpression right = (BinaryExpression) expr.right();
        assertEquals(right.left(), new NumValue(3));
        assertEquals(right.op(), Operation.MUL);
        assertEquals(right.right(), new NumValue(2));
    }
    //endregion

    //region
    @Test(groups = "error", description = "Парсинг с нечисловым значением после оператора")
    public void testParseNaryExpression_InvalidNumberAfterOperator() {
        String source = "5+abc";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с пустым источником")
    public void testParseNaryExpression_EmptySource() {
        String source = "";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с оператором в начале")
    public void testParseNaryExpression_OperatorAtStart() {
        String source = "+5";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с оператором в конце")
    public void testParseNaryExpression_OperatorAtEnd() {
        String source = "5+";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "error", description = "Парсинг с неизвестным оператором")
    public void testParseNaryExpression_UnknownOperator() {
        String source = "5@3";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isEmpty());
    }
    //endregion

    //region
    @Test(groups = "edge", description = "Парсинг с нулём: 0 + 1 - 2")
    public void testParseNaryExpression_ZeroOperations() {
        String source = "0+1-2";
        Optional<ParseResult<Expression>> result = parseNaryExpression(source, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end(), source.length());

        BinaryExpression expr = (BinaryExpression) result.get().value();
        assertEquals(expr.left(), new NumValue(0));
        assertEquals(expr.op(), Operation.ADD);

        BinaryExpression right = (BinaryExpression) expr.right();
        assertEquals(right.left(), new NumValue(1));
        assertEquals(right.op(), Operation.SUB);
        assertEquals(right.right(), new NumValue(2));
    }
    //endregion
}