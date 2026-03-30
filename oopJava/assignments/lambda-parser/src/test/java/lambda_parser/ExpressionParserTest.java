package lambda_parser;

import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class ExpressionParserTest {
    ExpressionParser parser = new ExpressionParser();

    @Test(description = "Операция с двумя числами, оператор отделен пробелами")
    void testTwoIntsSumWithSpaces() {
        Optional<ParseResult<Combined>> result = parser.parse("1 + 2", 0);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().value().head());
        assertEquals(BinaryOperatorParser.Operation.ADD, result.get().value().tail().get(0).operator());
        assertEquals(2, result.get().value().tail().get(0).value());
        assertEquals(5, result.get().end_offset());
    }

    @Test(description = "Операция с двумя числами, оператор не отделен пробелами")
    void testTwoIntsSumNoSpaces() {
        Optional<ParseResult<Combined>> result = parser.parse("123-456", 0);
        assertTrue(result.isPresent());
        assertEquals(123, result.get().value().head());
        assertEquals(BinaryOperatorParser.Operation.SUB, result.get().value().tail().get(0).operator());
        assertEquals(456, result.get().value().tail().get(0).value());
        assertEquals(7, result.get().end_offset());
    }

    @Test(description = "Операция с тремя числами, количество пробелов от 0 до 3")
    void testThreeIntsSumMixedSpaces() {
        Optional<ParseResult<Combined>> result = parser.parse("123* 456  /   789", 0);
        assertTrue(result.isPresent());
        assertEquals(123, result.get().value().head());
        
        Suffix suffix0 = result.get().value().tail().get(0);
        assertEquals(BinaryOperatorParser.Operation.MUL, suffix0.operator());
        assertEquals(456, suffix0.value());
        
        Suffix suffix1 = result.get().value().tail().get(1);
        assertEquals(BinaryOperatorParser.Operation.DIV, suffix1.operator());
        assertEquals(789, suffix1.value());
        
        assertEquals(17, result.get().end_offset());
    }

    @Test(description = "оператор есть, числа нет => ошибка")
    void testBrokenSuffix() {
        Optional<ParseResult<Combined>> result = parser.parse("123*456  /", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Integer < 0 поддерживаются")
    void testFirstNegIntsSupported() {
        Optional<ParseResult<Combined>> result = parser.parse("-123 * 456", 0);
        assertTrue(result.isPresent());
        assertEquals(-123, result.get().value().head());

        Suffix suffix0 = result.get().value().tail().get(0);
        assertEquals(BinaryOperatorParser.Operation.MUL, suffix0.operator());
        assertEquals(456, suffix0.value());

        assertEquals(10, result.get().end_offset());
    }

    @Test(description = "Integer < 0 поддерживаются")
    void testSecondNegIntsSupported() {
        Optional<ParseResult<Combined>> result = parser.parse("123 * -456", 0);
        assertTrue(result.isPresent());
        assertEquals(123, result.get().value().head());

        Suffix suffix0 = result.get().value().tail().get(0);
        assertEquals(BinaryOperatorParser.Operation.MUL, suffix0.operator());
        assertEquals(-456, suffix0.value());

        assertEquals(10, result.get().end_offset());
    }

    @Test(description = "Выход за диапазон Integer => некорректное значение")
    void testIntOutOfBound() {
        Optional<ParseResult<Combined>> result = parser.parse("2147483648", 0);
        assertTrue(result.isEmpty());
    }

}