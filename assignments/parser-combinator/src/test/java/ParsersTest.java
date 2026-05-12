import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class ParsersTest {

    @Test(description = "символ совпал")
    public void testCharMatch() {

        Parser<Character> parser = Parsers.characterParser('a');
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), 'a');
        assertEquals(result.get().offset(), 1);
    }

    @Test(description = "символ есть, не найден")
    public void testCharMisMatch() {

        Parser<Character> parser = Parsers.characterParser('b');
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertTrue(result.isEmpty());
    }

    @Test(description = "символа нет, не найден")
    public void testOffsetOutOfBound() {

        Parser<Character> parser = Parsers.characterParser('a');
        Optional<Parsed<Character>> result = parser.apply("", 0);

        assertTrue(result.isEmpty());
    }


    @Test
    public void testDigitParser() {

        Parser<Character> parser = Parsers.digitParser('1');
        Optional<Parsed<Character>> result = parser.apply("1", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), '1');
        assertEquals(result.get().offset(), 1);
    }

    @Test
    public void testStringParser() {
        Parser<String> parser = Parsers.stringParser("hello");
        Optional<Parsed<String>> result = parser.apply("hello", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "hello");
        assertEquals(result.get().offset(), 5);
    }

    @Test
    public void testWhitespaceParser() {
        Parser<Character> parser = Parsers.whitespaceParser();
        Optional<Parsed<Character>> result = parser.apply(" ", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), ' ');
        assertEquals(result.get().offset(), 1);
    }
}