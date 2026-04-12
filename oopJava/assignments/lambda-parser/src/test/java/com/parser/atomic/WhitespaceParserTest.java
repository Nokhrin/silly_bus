package com.parser.atomic;

import com.parser.TestConfig;
import com.parser.core.ParseResult;
import com.parser.core.Whitespace;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class WhitespaceParserTest {

    static {
        TestConfig.enableDebugLogging(WhitespaceParser.class);
    }

    private WhitespaceParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new WhitespaceParser();
    }

    @Test(description = "Проверка распознавания ASCII пробела")
    public void testIsWhitespace_space() {
        assertTrue(parser.isWhitespace(' '));
    }

    @Test(description = "Проверка распознавания горизонтальной табуляции")
    public void testIsWhitespace_tab() {
        assertTrue(parser.isWhitespace('\t'));
    }

    @Test(description = "Проверка распознавания перевода строки (LF)")
    public void testIsWhitespace_newline() {
        assertTrue(parser.isWhitespace('\n'));
    }

    @Test(description = "Проверка распознавания возврата каретки (CR)")
    public void testIsWhitespace_carriageReturn() {
        assertTrue(parser.isWhitespace('\r'));
    }

    @Test(description = "Проверка нераспознавания непробела как whitespace")
    public void testIsWhitespace_letter() {
        assertFalse(parser.isWhitespace('a'));
    }

    @Test(description = "Парсинг одного пробела")
    public void testParse_singleSpace() {
        Optional<ParseResult<Whitespace>> result = parser.parse(" text", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end_offset(), 1);
        assertSame(result.get().value(), Whitespace.INSTANCE);
    }

    @Test(description = "Парсинг нескольких пробелов")
    public void testParse_multipleSpaces() {
        Optional<ParseResult<Whitespace>> result = parser.parse("    text", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().end_offset(), 4);
    }

}