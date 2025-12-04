package CalcParser;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NaryExpressionTest {

    @Test(description = "Парсинг простого сложения: 5 + 3")
    public void testParseAddSubExpression_SimpleAddition() {
        var result = NaryExpression.parseAddSubExpression("5 + 3", 0);
        assertEquals(result.get().value().evaluate(), 8.0);
    }

    @Test(description = "Парсинг простого вычитания: 10 - 4")
    public void testParseAddSubExpression_SimpleSubtraction() {
        var result = NaryExpression.parseAddSubExpression("10 - 4", 0);
        assertEquals(result.get().value().evaluate(), 6.0);
    }

    @Test(description = "Парсинг сложения и умножения: 2 + 3 * 4")
    public void testParseAddSubExpression_MixedAdditionMultiplication() {
        var result = NaryExpression.parseAddSubExpression("2 + 3 * 4", 0);
        assertEquals(result.get().value().evaluate(), 14.0);
    }

    @Test(description = "Парсинг умножения и сложения: 3 * 4 + 2")
    public void testParseAddSubExpression_MixedMultiplicationAddition() {
        var result = NaryExpression.parseAddSubExpression("3 * 4 + 2", 0);
        assertEquals(result.get().value().evaluate(), 14.0);
    }

    @Test(description = "Парсинг с пробелами: 5 + 3 * 2")
    public void testParseAddSubExpression_WithWhitespaces() {
        var result = NaryExpression.parseAddSubExpression("5 + 3 * 2", 0);
        assertEquals(result.get().value().evaluate(), 11.0);
    }

    @Test(description = "Парсинг с табуляцией: 10 - 2 * 3")
    public void testParseAddSubExpression_WithTab() {
        var result = NaryExpression.parseAddSubExpression("10 - 2\t* 3", 0);
        assertEquals(result.get().value().evaluate(), 4.0);
    }

    @Test(description = "Парсинг с переносами строк: 1 + 2\n* 3")
    public void testParseAddSubExpression_WithNewLines() {
        var result = NaryExpression.parseAddSubExpression("1 + 2\n* 3", 0);
        assertEquals(result.get().value().evaluate(), 7.0);
    }

    @Test(description = "Парсинг с несколькими операциями: 1 + 2 + 3")
    public void testParseAddSubExpression_MultipleAdditions() {
        var result = NaryExpression.parseAddSubExpression("1 + 2 + 3", 0);
        assertEquals(result.get().value().evaluate(), 6.0);
    }

    @Test(description = "Парсинг с несколькими операциями: 10 - 3 - 2")
    public void testParseAddSubExpression_MultipleSubtractions() {
        var result = NaryExpression.parseAddSubExpression("10 - 3 - 2", 0);
        assertEquals(result.get().value().evaluate(), 5.0);
    }

    @Test(description = "Парсинг с умножением и сложением: 2 * 3 + 4 * 5")
    public void testParseAddSubExpression_MixedOperations() {
        var result = NaryExpression.parseAddSubExpression("2 * 3 + 4 * 5", 0);
        assertEquals(result.get().value().evaluate(), 26.0);
    }

    @Test(description = "Парсинг с отрицательным числом: -5 + 10")
    public void testParseAddSubExpression_NegativeNumber() {
        var result = NaryExpression.parseAddSubExpression("-5 + 10", 0);
        assertEquals(result.get().value().evaluate(), 5.0);
    }

    @Test(description = "Парсинг с отрицательным числом и умножением: -2 * 3 + 1")
    public void testParseAddSubExpression_NegativeNumberAndMultiplication() {
        var result = NaryExpression.parseAddSubExpression("-2 * 3 + 1", 0);
        assertEquals(result.get().value().evaluate(), -5.0);
    }

    @Test(description = "Парсинг одного числа: 42")
    public void testParseAddSubExpression_SingleNumber() {
        var result = NaryExpression.parseAddSubExpression("42", 0);
        assertEquals(result.get().value().evaluate(), 42.0);
    }

    @Test(description = "Парсинг с вложенными операциями: 1 + 2 * 3 + 4")
    public void testParseAddSubExpression_ComplexExpression() {
        var result = NaryExpression.parseAddSubExpression("1 + 2 * 3 + 4", 0);
        assertEquals(result.get().value().evaluate(), 11.0);
    }
}