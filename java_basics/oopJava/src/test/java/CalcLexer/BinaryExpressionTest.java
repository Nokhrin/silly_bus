package CalcLexer;

import org.testng.annotations.Test;

import java.util.Optional;

import static CalcLexer.BinaryExpression.parseBinOpSimple;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BinaryExpressionTest {
    //region testNumWsOpWsNum
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения 'num ws op ws num': 123 + 456")
    public void testNumWsOpWsNum() {
        String input = "123 + 456";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        // Проверка, что результат - это BinOp
        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        // Проверка значений
        assertEquals(binOp.left(), 123, "Левый операнд должен быть 123");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 456, "Правый операнд должен быть 456");

        // Проверка оценки
        assertEquals(binOp.evaluate(), 579.0, 1e-9, "Результат сложения 123 + 456 должен быть 579.0");
    }
    //endregion

    //region testNumOpWsNum
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения 'num op ws num': 123+ 456")
    public void testNumOpWsNum() {
        String input = "123+ 456";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        // Проверка, что результат - это BinOp
        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        // Проверка значений
        assertEquals(binOp.left(), 123, "Левый операнд должен быть 123");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 456, "Правый операнд должен быть 456");

        // Проверка оценки
        assertEquals(binOp.evaluate(), 579.0, 1e-9, "Результат сложения 123 + 456 должен быть 579.0");
    }
    //endregion

    //region testNumWsOpNum
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения 'num ws op num': 123 +456")
    public void testNumWsOpNum() {
        String input = "123 +456";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        // Проверка, что результат — это BinOp
        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        // Проверка значений
        assertEquals(binOp.left(), 123, "Левый операнд должен быть 123");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 456, "Правый операнд должен быть 456");

        // Проверка оценки
        assertEquals(binOp.evaluate(), 579.0, 1e-9, "Результат сложения 123 + 456 должен быть 579.0");
    }
    //endregion

    //region testNumOpNum
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения 'num op num': 123+456")
    public void testNumOpNum() {
        String input = "123+456";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        // Проверка, что результат — это BinOp
        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        // Проверка значений
        assertEquals(binOp.left(), 123, "Левый операнд должен быть 123");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 456, "Правый операнд должен быть 456");

        // Проверка оценки
        assertEquals(binOp.evaluate(), 579.0, 1e-9, "Результат сложения 123 + 456 должен быть 579.0");
    }
    //endregion

    //region TestName: Парсинг вычитания
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения: 100 - 25")
    public void testParseSubtraction() {
        String input = "100 - 25";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        assertEquals(binOp.left(), 100, "Левый операнд должен быть 100");
        assertEquals(binOp.op(), Parsers.Operation.SUB, "Оператор должен быть SUB");
        assertEquals(binOp.right(), 25, "Правый операнд должен быть 25");

        assertEquals(binOp.evaluate(), 75.0, 1e-9, "Результат вычитания 100 - 25 должен быть 75.0");
    }
    //endregion

    //region TestName: Парсинг умножения
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения: 7 * 8")
    public void testParseMultiplication() {
        String input = "7 * 8";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        assertEquals(binOp.left(), 7, "Левый операнд должен быть 7");
        assertEquals(binOp.op(), Parsers.Operation.MUL, "Оператор должен быть MUL");
        assertEquals(binOp.right(), 8, "Правый операнд должен быть 8");

        assertEquals(binOp.evaluate(), 56.0, 1e-9, "Результат умножения 7 * 8 должен быть 56.0");
    }
    //endregion

    //region TestName: Парсинг деления
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга и оценки выражения: 100 / 4")
    public void testParseDivision() {
        String input = "100 / 4";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения");
        ParseResult<Expression> parseResult = result.get();

        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        assertEquals(binOp.left(), 100, "Левый операнд должен быть 100");
        assertEquals(binOp.op(), Parsers.Operation.DIV, "Оператор должен быть DIV");
        assertEquals(binOp.right(), 4, "Правый операнд должен быть 4");

        assertEquals(binOp.evaluate(), 25.0, 1e-9, "Результат деления 100 / 4 должен быть 25.0");
    }
    //endregion

    //region TestName: Парсинг с пробелами вокруг оператора
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга с пробелами: `10  +  20`")
    public void testParseWithWhitespace() {
        String input = "10  +  20";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения с пробелами вокруг оператора");
        ParseResult<Expression> parseResult = result.get();

        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        assertEquals(binOp.left(), 10, "Левый операнд должен быть 10");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 20, "Правый операнд должен быть 20");

        assertEquals(binOp.evaluate(), 30.0, 1e-9, "Результат сложения 10 + 20 должен быть 30.0");
    }
    //endregion

    //region TestName: Парсинг отрицательного числа и сложения
    @Test(groups = {"simpleParser", "binOp"}, description = "Проверка парсинга: -5 + 3")
    public void testParseNegativeNumberWithAddition() {
        String input = "-5 + 3";
        Optional<ParseResult<Expression>> result = parseBinOpSimple(input, 0);

        assertTrue(result.isPresent(), "Ожидается успешный парсинг выражения с отрицательным числом");
        ParseResult<Expression> parseResult = result.get();

        assertTrue(parseResult.value() instanceof BinOp, "Ожидается объект типа BinOp");
        BinOp binOp = (BinOp) parseResult.value();

        assertEquals(binOp.left(), -5, "Левый операнд должен быть -5");
        assertEquals(binOp.op(), Parsers.Operation.ADD, "Оператор должен быть ADD");
        assertEquals(binOp.right(), 3, "Правый операнд должен быть 3");

        assertEquals(binOp.evaluate(), -2.0, 1e-9, "Результат сложения -5 + 3 должен быть -2.0");
    }
    //endregion
}