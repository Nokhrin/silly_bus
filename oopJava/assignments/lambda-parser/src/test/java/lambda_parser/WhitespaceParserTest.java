package lambda_parser;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WhitespaceParserTest {
    private final Parser<String> wsParser = new WhitespaceParser();

    @Test(description = "Один пробел")
    void testOneSpace() {
        var result = wsParser.parse(" ", 0);
        assertTrue(result.isPresent());
        assertEquals("", result.get().value());
        assertEquals(1, result.get().end_offset());
    }

    @Test(description = "Нет пробела")
    void testNoSpace() {
        var result = wsParser.parse("", 0);
        assertTrue(result.isPresent());
        assertEquals("", result.get().value());
        assertEquals(0, result.get().end_offset());
    }

    @Test(description = "Пробельные символы: r,n,t,' '")
    void testMixedSpaces() {
        var result = wsParser.parse("\r\n\t ", 0);
        assertTrue(result.isPresent());
        assertEquals("", result.get().value());
        assertEquals(4, result.get().end_offset());
    }

}