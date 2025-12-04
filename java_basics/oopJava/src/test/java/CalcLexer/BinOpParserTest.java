package CalcLexer;

import org.testng.annotations.Test;

import java.util.Optional;

import static CalcLexer.BinOpParser.parseBinOpChain;
import static CalcLexer.BinOpParser.parseBinOpSimple;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BinOpParserTest {

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
        Optional<ParseResult<Expression>> result = parseBinOpChain("42", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new NumValue(42));
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Сложение двух чисел без пробелов: 1+2", groups = "ParseChain")
    public void testParseAdditionTwoNoSpaces() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("1+2", 0);
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
        Optional<ParseResult<Expression>> result = parseBinOpChain("1 + 2", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение двух чисел с табуляцией: 1\t+\t2", groups = "ParseChain")
    public void testParseAdditionTwoWithTabs() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("1\t+\t2", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение трех чисел: 1+2+3", groups = "ParseChain")
    public void testParseAdditionThreeNoSpaces() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("1+2+3", 0);
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
        Optional<ParseResult<Expression>> result = parseBinOpChain("1 + 2 + 3", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 6.0, 0.0001);
    }

    @Test(description = "Сложение четырех чисел: 1+2+3+4", groups = "ParseChain")
    public void testParseAdditionFourNoSpaces() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("1+2+3+4", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 10.0, 0.0001);
    }

    @Test(description = "Сложение четырех чисел с пробелом и табуляцией: 1 + 2\t+\t3 + 4", groups = "ParseChain")
    public void testParseAdditionFourWithComplexWhitespace() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("1 + 2\t+\t3 + 4", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 10.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем: 01 + 02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZeros() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("01+02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
        // Проверка, что числа парсятся как целые, ведущие нули допустимы
        assertEquals(((BinOpExpression) result.get().value()).left(), new NumValue(1));
        assertEquals(((BinOpExpression) result.get().value()).right(), new NumValue(2));
    }

    @Test(description = "Сложение с ведущим нулем и пробелами: 01 + 02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndSpaces() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("01 + 02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем и табуляцией: 01\t+\t02", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndTabs() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("01\t+\t02", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 3.0, 0.0001);
    }

    @Test(description = "Сложение с ведущим нулем, пробелами и табуляцией: 01 + 02 + 03", groups = "ParseChain")
    public void testParseAdditionWithLeadingZerosAndMixedWhitespace() {
        Optional<ParseResult<Expression>> result = parseBinOpChain("01 + 02\t+03", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value().evaluate(), 6.0, 0.0001);
    }

    //endregion Operations Chain
    
    
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