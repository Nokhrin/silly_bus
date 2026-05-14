import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

/**
 * левый успех (правый не вызван), левый отказ + правый успех, оба отказа.
 */
public class ParserOrTest {
    @Test
    public void testOrLeftFailed() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Either<Character, Character>> twoChars = p1.or(p2);
        Optional<Parsed<Either<Character, Character>>> result = twoChars.apply("bb", 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value().isRight());
        assertEquals(result.get().value().right(), 'b');
        assertEquals(result.get().offset(), 1);
    }
}