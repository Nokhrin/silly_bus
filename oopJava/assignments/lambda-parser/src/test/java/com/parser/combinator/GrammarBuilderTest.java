package com.parser.combinator;

import com.parser.core.*;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class GrammarBuilderTest {
    @Test(description = "Парсинг выражения со всеми реализованными атомами и пробелами")
    public void testParse_AllAtomsWithWhitespaces() {
        Parser<Combined> parser = GrammarBuilder.getExpressionParser();

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
        Parser<Combined> parser = GrammarBuilder.getExpressionParser();

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

}