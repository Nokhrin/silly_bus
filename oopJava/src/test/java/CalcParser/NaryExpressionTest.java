package CalcParser;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NaryExpressionTest {

    @Test(description = "Парсинг простого сложения: 5 + 3")
    public void testParseNaryExpression_SimpleAddition() {
        var result = NaryExpression.parseNaryExpression("5 + 3", 0);
        assertEquals(result.get().value().evaluate(), 8.0);
    }

    @Test(description = "Парсинг простого вычитания: 10 - 4")
    public void testParseNaryExpression_SimpleSubtraction() {
        var result = NaryExpression.parseNaryExpression("10 - 4", 0);
        assertEquals(result.get().value().evaluate(), 6.0);
    }

    @Test(description = "Парсинг сложения и умножения: 2 + 3 * 4")
    public void testParseNaryExpression_MixedAdditionMultiplication() {
        var result = NaryExpression.parseNaryExpression("2 + 3 * 4", 0);
        assertEquals(result.get().value().evaluate(), 14.0);
    }

    @Test(description = "Парсинг умножения и сложения: 3 * 4 + 2")
    public void testParseNaryExpression_MixedMultiplicationAddition() {
        var result = NaryExpression.parseNaryExpression("3 * 4 + 2", 0);
        assertEquals(result.get().value().evaluate(), 14.0);
    }

    @Test(description = "Парсинг с пробелами: 5 + 3 * 2")
    public void testParseNaryExpression_WithWhitespaces() {
        var result = NaryExpression.parseNaryExpression("5 + 3 * 2", 0);
        assertEquals(result.get().value().evaluate(), 11.0);
    }

    @Test(description = "Парсинг с табуляцией: 10 - 2 * 3")
    public void testParseNaryExpression_WithTab() {
        var result = NaryExpression.parseNaryExpression("10 - 2\t* 3", 0);
        assertEquals(result.get().value().evaluate(), 4.0);
    }

    @Test(description = "Парсинг с переносами строк: 1 + 2\n* 3")
    public void testParseNaryExpression_WithNewLines() {
        var result = NaryExpression.parseNaryExpression("1 + 2\n* 3", 0);
        assertEquals(result.get().value().evaluate(), 7.0);
    }

    @Test(description = "Парсинг с несколькими операциями: 1 + 2 + 3")
    public void testParseNaryExpression_MultipleAdditions() {
        var result = NaryExpression.parseNaryExpression("1 + 2 + 3", 0);
        assertEquals(result.get().value().evaluate(), 6.0);
    }

    @Test(description = "Парсинг с несколькими операциями: 10 - 3 - 2")
    public void testParseNaryExpression_MultipleSubtractions() {
        var result = NaryExpression.parseNaryExpression("10 - 3 - 2", 0);
        assertEquals(result.get().value().evaluate(), 5.0);
    }

    @Test(description = "Парсинг с умножением и сложением: 2 * 3 + 4 * 5")
    public void testParseNaryExpression_MixedOperations() {
        var result = NaryExpression.parseNaryExpression("2 * 3 + 4 * 5", 0);
        assertEquals(result.get().value().evaluate(), 26.0);
    }

    @Test(description = "Парсинг с отрицательным числом: -5 + 10")
    public void testParseNaryExpression_NegativeNumber() {
        var result = NaryExpression.parseNaryExpression("-5 + 10", 0);
        assertEquals(result.get().value().evaluate(), 5.0);
    }

    @Test(description = "Парсинг с отрицательным числом и умножением: -2 * 3 + 1")
    public void testParseNaryExpression_NegativeNumberAndMultiplication() {
        var result = NaryExpression.parseNaryExpression("-2 * 3 + 1", 0);
        assertEquals(result.get().value().evaluate(), -5.0);
    }

    @Test(description = "Парсинг одного числа: 42")
    public void testParseNaryExpression_SingleNumber() {
        var result = NaryExpression.parseNaryExpression("42", 0);
        assertEquals(result.get().value().evaluate(), 42.0);
    }

    @Test(description = "Парсинг с вложенными операциями: 1 + 2 * 3 + 4")
    public void testParseNaryExpression_ComplexExpression() {
        var result = NaryExpression.parseNaryExpression("1 + 2 * 3 + 4", 0);
        assertEquals(result.get().value().evaluate(), 11.0);
    }
}