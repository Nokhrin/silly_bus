package lambda_parser;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class IntParserTest {
    
    private final Parser<Integer> integerParser = new IntParser();
    
    @Test(description = "Положительное целое")
    void testPosInt() {
        var result = integerParser.parse("123", 0);
        assertTrue(result.isPresent());
        assertEquals(123, result.get().value());
        assertEquals(3, result.get().end_offset());
    }

    @Test(description = "Отрицательное целое")
    void testNegInt() {
        var result = integerParser.parse("-123", 0);
        assertTrue(result.isPresent());
        assertEquals(-123, result.get().value());
        assertEquals(4, result.get().end_offset());
    }

    @Test(description = "Пустая строка")
    void testEmptySource() {
        var result = integerParser.parse("", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Непустая строка, содержит НЕцифры, начинается с цифр")
    void testDigitsLettersSource() {
        var result = integerParser.parse("123qwe456", 0);
        assertTrue(result.isPresent());
        assertEquals(123, result.get().value());
        assertEquals(3, result.get().end_offset());
    }

    @Test(description = "Непустая строка, содержит НЕцифры, начинается с НЕцифр")
    void testLettersDigitsSource() {
        var result = integerParser.parse("qwe456", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Минус без цифр")
    void testNonDigitalSourceWithMinus() {
        var result = integerParser.parse("-asdf", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Переполнение Integer >0")
    void testIntegerMaxValue() {
        var result = integerParser.parse("99999999999999999", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Переполнение Integer <0")
    void testIntegerMinValue() {
        var result = integerParser.parse("-99999999999999999", 0);
        assertTrue(result.isEmpty());
    }

}