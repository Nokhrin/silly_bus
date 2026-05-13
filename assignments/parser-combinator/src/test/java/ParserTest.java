import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class ParserTest {

    @Test
    public void testApply() {
    }

    @Test
    public void testPlusTwoParsersMatch() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Tuple<Character, Character>> twoChars = p1.plus(p2);
        Optional<Parsed<Tuple<Character, Character>>> result = twoChars.apply("ab", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().left(), 'a');
        assertEquals(result.get().value().right(), 'b');
    }

    @Test
    public void testPlusLeftFailed() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Tuple<Character, Character>> twoChars = p1.plus(p2);
        Optional<Parsed<Tuple<Character, Character>>> result = twoChars.apply("bb", 0);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testPlusRightFailed() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Tuple<Character, Character>> twoChars = p1.plus(p2);
        Optional<Parsed<Tuple<Character, Character>>> result = twoChars.apply("aa", 0);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testPlusThreeParsersMatch() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Character> p3 = Parsers.characterParser('c');
        Parser<Tuple<Tuple<Character, Character>, Character>> threeChars = (p1.plus(p2)).plus(p3);
        Optional<Parsed<Tuple<Tuple<Character, Character>, Character>>> result = threeChars.apply("abc", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().left().left(), 'a');
        assertEquals(result.get().value().left().right(), 'b');
        assertEquals(result.get().value().right(), 'c');
    }
}