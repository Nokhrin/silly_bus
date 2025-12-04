package CalcLexer;

import org.testng.annotations.Test;

import java.util.Optional;

import static CalcLexer.NaryExpression.parseNaryExpression;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NaryExpressionTest {

    //region Operations Chain

    /**
     * Тестовые сценарии
     *     1, 2, 3, 4 операнда
     *     Ведущие нули (наличие/отсутствие)
     *     Пробелы и табуляции везде, где разрешены по правилу {ws}
     *     Корректная обработка + как оператора
     *     Правильная работа рекурсии и правоассоциативности
     *     Проверка Optional<ParseResult<Expression>> на успех
     */

    @Test(description = "Одно число: 42 -> 42", groups = "ParseChain")
    public void testParseSingleNumber() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("42", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new NumValue(42));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Одно число: 42 -> 42, Старт за пределами строки", groups = "ParseChain")
    public void testParseSingleNumberFalseStart() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("42", 3);
        assertTrue(result.isEmpty(), "Должно быть Optional.empty()");
    }

    @Test(description = "Сложение двух чисел без пробелов: 1+2", groups = "ParseChain")
    public void testParseAdditionTwoNoSpaces() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1+2", 0);
        assertTrue(result.isPresent());
        Expression expr = result.get().value();
        assertEquals(expr.evaluate(), 3.0, 0.0001);
        assertTrue(expr instanceof BinOpExpression);
        assertEquals(((BinOpExpression) expr).left(), new NumValue(1));
        assertEquals(((BinOpExpression) expr).op(), Parsers.Operation.ADD);
        assertEquals(((BinOpExpression) expr).right(), new NumValue(2));
    }

    @Test(description = "Сложение двух чисел с пробелами: 1 + 2", groups = "ParseChain")
    public void testParseAdditionTwoWithSpaces() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1 + 2", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение двух чисел с табуляцией: 1\t+\t2", groups = "ParseChain")
    public void testParseAdditionTwoWithTabs() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1\t+\t2", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение трех чисел: 1+2+3", groups = "ParseChain")
    public void testParseAdditionThreeNoSpaces() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1+2+3", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 6.0, 0.0001);
        assertTrue(result.get().value() instanceof BinOpExpression);
        BinOpExpression binOp = (BinOpExpression) result.get().value();
        assertEquals(binOp.left(), new NumValue(1));
        assertEquals(binOp.op(), Parsers.Operation.ADD);
        assertTrue(binOp.right() instanceof BinOpExpression);
        assertEquals(((BinOpExpression) binOp.right()).left(), new NumValue(2));
        assertEquals(((BinOpExpression) binOp.right()).op(), Parsers.Operation.ADD);
        assertEquals(((BinOpExpression) binOp.right()).right(), new NumValue(3));
    }

    @Test(description = "Сложение трех чисел с пробелами и табуляцией: 1 + 2 + 3", groups = "ParseChain")
    public void testParseAdditionThreeWithWhitespace() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1 + 2 + 3", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 6.0, 0.0001);
    }

    @Test(description = "Сложение четырех чисел: 1+2+3+4", groups = "ParseChain")
    public void testParseAdditionFourNoSpaces() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1+2+3+4", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 10.0, 0.0001);
    }

    @Test(description = "Сложение четырех чисел с пробелом и табуляцией: 1 + 2\t+\t3 + 4", groups = "ParseChain")
    public void testParseAdditionFourWithComplexWhitespace() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("1 + 2\t+\t3 + 4", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 10.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем: 01 + 02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZeros() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("01+02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
        // Проверка, что числа парсятся как целые, ведущие нули допустимы
        assertEquals(((BinOpExpression) result.get().value()).left(), new NumValue(1));
        assertEquals(((BinOpExpression) result.get().value()).right(), new NumValue(2));
    }

    @Test(description = "Сложение с ведущим нулем и пробелами: 01 + 02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndSpaces() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("01 + 02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем и табуляцией: 01\t+\t02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndTabs() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("01\t+\t02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем, пробелами и табуляцией: 01 + 02 + 03", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndMixedWhitespace() {
        Optional<ParseResult<Expression>> result = parseNaryExpression("01 + 02\t+03", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 6.0, 0.0001);
    }

    //endregion Operations Chain
}
