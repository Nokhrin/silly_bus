package com.parser.atomic;

import com.parser.TestConfig;
import com.parser.core.BinaryOperator;
import com.parser.core.ParseResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class BinaryOperatorParserTest {

    static {
        TestConfig.enableDebugLogging(IntParser.class);
    }

    private BinaryOperatorParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new BinaryOperatorParser();
    }

    @Test(description = "Парсинг оператора сложения '+'")
    public void testParse_add() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("+123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), BinaryOperator.ADD);
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "Парсинг оператора вычитания '-'")
    public void testParse_sub() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("-123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), BinaryOperator.SUB);
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "Парсинг оператора умножения '*'")
    public void testParse_mul() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("*123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), BinaryOperator.MUL);
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "Парсинг оператора деления '/'")
    public void testParse_div() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("/123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), BinaryOperator.DIV);
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "Парсинг из середины строки (offset > 0)")
    public void testParse_withOffset() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("12+34", 2);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), BinaryOperator.ADD);
        assertEquals(result.get().end_offset(), 3);
    }

    @Test(description = "Неудача: пустая строка")
    public void testParse_emptyString() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: offset за границей")
    public void testParse_offsetOutOfBounds() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("+", 5);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: null строка")
    public void testParse_nullString() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse(null, 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: символ не является оператором (буква)")
    public void testParse_invalidChar_letter() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("a", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: символ не является оператором (цифра)")
    public void testParse_invalidChar_digit() {
        Optional<ParseResult<BinaryOperator>> result = parser.parse("1", 0);
        assertFalse(result.isPresent());
    }
}