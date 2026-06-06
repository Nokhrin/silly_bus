package com.parser.combinator;

import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import com.parser.core.*;
import com.parser.testHelpers.MockStringParser;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class PlusParserTest {
    /**
     * Парсер Integer
     */
    IntParser integerParser = new IntParser();

    /**
     * Парсер пробельных символов
     */
    WhitespaceParser whitespaceParser = new WhitespaceParser();


    @Test(description = "Без skip")
    public void testPlus_basic() {
        Parser<String> charParser = new MockStringParser("x");
        Plus<Integer, String> plus = integerParser.plus(charParser);
        Optional<ParseResult<Tuple2<Integer, String>>> result = plus.parse("123x", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().a(), Integer.valueOf(123));
        assertEquals(result.get().value().b(), "x");
        assertEquals(result.get().end_offset(), 4);
    }

    @Test(description = "SkipRight")
    public void testSkipRight() {
        Parser<String> charParser = new MockStringParser("x");
        Parser<Integer> parser = integerParser.plus(charParser).skipRight();
        Optional<ParseResult<Integer>> result = parser.parse("123x", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(123));
        assertEquals(result.get().end_offset(), 4);
    }

    @Test(description = "SkipLeft")
    public void testSkipLeft() {
        Parser<String> charParser = new MockStringParser("x");
        Parser<String> parser = integerParser.plus(charParser).skipLeft();
        Optional<ParseResult<String>> result = parser.parse("123x", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "x");
        assertEquals(result.get().end_offset(), 4);
    }

    @Test(description = "SkipLeft + SkipRight => пропуск пробела слева и пропуск пробела справа")
    public void testSkipLeftSkipRight_withOptional() {
        Parser<String> wsParser = new WhitespaceParser().map(w -> "ws");
        Parser<String> charParser = new MockStringParser("w");
        Parser<String> parser =
                wsParser.optional()
                .plus(charParser).skipLeft()
                .plus(wsParser.optional()).skipRight();

        Optional<ParseResult<String>> result = parser.parse("  w   ", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "w");
        assertEquals(result.get().end_offset(), 6);
    }

}