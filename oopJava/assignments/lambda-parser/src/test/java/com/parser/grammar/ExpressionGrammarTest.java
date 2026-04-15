package com.parser.grammar;

import com.parser.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ExpressionGrammarTest {
    static final Logger log = LoggerFactory.getLogger(ExpressionGrammarTest.class);
    /**
     * Парсер выражения
     */
    Parser<Combined> expressionParser = ExpressionGrammar.build();

    @Test(description = "Парсинг выражения со всеми реализованными атомами и пробелами")
    public void testParse_AllAtomsWithWhitespaces() {
        Parser<Combined> parser = ExpressionGrammar.build();

        Optional<ParseResult<Combined>> result = parser.parse("1 + 2 - 3 * 4 / 5", 0);
        assertTrue(result.isPresent());

        Combined combined = result.get().value();
        assertEquals(combined.head(), Integer.valueOf(1));

        List<Suffix> tail = combined.tail();
        assertEquals(tail.size(), 4);

        assertEquals(tail.get(0).operator(), BinaryOperator.ADD);
        assertEquals(tail.get(0).value(), Integer.valueOf(2));

        assertEquals(tail.get(1).operator(), BinaryOperator.SUB);
        assertEquals(tail.get(1).value(), Integer.valueOf(3));

        assertEquals(tail.get(2).operator(), BinaryOperator.MUL);
        assertEquals(tail.get(2).value(), Integer.valueOf(4));

        assertEquals(tail.get(3).operator(), BinaryOperator.DIV);
        assertEquals(tail.get(3).value(), Integer.valueOf(5));

        assertEquals(result.get().end_offset(), 17);
    }

    @Test(description = "Парсинг выражения со всеми реализованными атомами, без пробелов")
    public void testParse_AllAtomsNoWhitespaces() {
        Parser<Combined> parser = ExpressionGrammar.build();

        Optional<ParseResult<Combined>> result = parser.parse("1+2-3*4/5", 0);
        assertTrue(result.isPresent());

        Combined combined = result.get().value();
        assertEquals(combined.head(), Integer.valueOf(1));

        List<Suffix> tail = combined.tail();
        assertEquals(tail.size(), 4);

        assertEquals(tail.get(0).operator(), BinaryOperator.ADD);
        assertEquals(tail.get(0).value(), Integer.valueOf(2));

        assertEquals(tail.get(1).operator(), BinaryOperator.SUB);
        assertEquals(tail.get(1).value(), Integer.valueOf(3));

        assertEquals(tail.get(2).operator(), BinaryOperator.MUL);
        assertEquals(tail.get(2).value(), Integer.valueOf(4));

        assertEquals(tail.get(3).operator(), BinaryOperator.DIV);
        assertEquals(tail.get(3).value(), Integer.valueOf(5));

        assertEquals(result.get().end_offset(), 9);
    }

    @Test(description = "Одиночное положительное число: head, no tail")
    public void testParse_singlePositiveNumber() {
        Optional<ParseResult<Combined>> combinedParseResult = expressionParser.parse("123", 0);

        assertTrue(combinedParseResult.isPresent());
        assertEquals(combinedParseResult.get().value().head(), 123);
        assertTrue(combinedParseResult.get().value().tail().isEmpty());
        assertEquals(combinedParseResult.get().end_offset(), 3);
    }

    @Test(description = "Одиночное отрицательное число: head, no tail")
    public void testParse_singleNegativeNumber() {
        Optional<ParseResult<Combined>> combinedParseResult = expressionParser.parse("-123", 0);

        assertTrue(combinedParseResult.isPresent());
        log.debug(combinedParseResult.toString());
        assertEquals(combinedParseResult.get().value().head(), -123);
        assertTrue(combinedParseResult.get().value().tail().isEmpty());
        assertEquals(combinedParseResult.get().end_offset(), 4);
    }

    @Test(description = "Успешный парсинг строки с невалидным хвостом")
    public void testParse_partial_withTrailing() {
        Optional<ParseResult<Combined>> combinedParseResult = expressionParser.parse("456 - 123 qwe", 0);

        assertTrue(combinedParseResult.isPresent());
        log.debug(combinedParseResult.toString());
        assertEquals(combinedParseResult.get().value().head(), 456);
        assertEquals(combinedParseResult.get().value().tail().getFirst().operator(), BinaryOperator.SUB);
        assertEquals(combinedParseResult.get().value().tail().getFirst().value(), 123);
        assertEquals(combinedParseResult.get().end_offset(), 9);
    }
}