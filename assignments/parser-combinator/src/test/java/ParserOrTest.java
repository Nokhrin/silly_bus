import org.testng.annotations.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.*;

/**
 * левый успех (правый не вызван),
 * левый отказ + правый успех,
 * оба отказа,
 * Валидация offset: успех левого -> смещение от левого; успех правого -> смещение от правого
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

    @Test
    public void testOrOffsetLeftSuccessRightFail() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Either<Character, Character>> twoChars = p1.or(p2);
        Optional<Parsed<Either<Character, Character>>> result = twoChars.apply("ac", 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value().isLeft());
        assertFalse(result.get().value().isRight());
        assertEquals(result.get().value().left(), 'a');
        assertEquals(result.get().offset(), 1);

    }

    @Test
    public void testOrLeftFailRightFail() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Either<Character, Character>> twoChars = p1.or(p2);
        Optional<Parsed<Either<Character, Character>>> result = twoChars.apply("cd", 0);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testOrOffsetLeftSuccessRightSkipped() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Either<Character, Character>> twoChars = p1.or(p2);
        Optional<Parsed<Either<Character, Character>>> result = twoChars.apply("ab", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().left(), 'a');
        assertTrue(result.get().value().isLeft());
        assertFalse(result.get().value().isRight());
        assertEquals(result.get().offset(), 1);

    }

    @Test
    public void testLeftSuccessRightNotCalled(){
        AtomicBoolean isRightCalled = new AtomicBoolean(false);

        Parser<Character> leftParser = (source, offset) ->
                Optional.of(new Parsed<>('a', offset+1));
        Parser<Character> rightParser = ((source, offset) -> {
            isRightCalled.set(true);
            return Optional.empty();
        });

        Parser<Either<Character, Character>> chainedParser = leftParser.or(rightParser);
        Optional<Parsed<Either<Character, Character>>> result = chainedParser.apply("abc", 0);

        assertTrue(result.isPresent());
        assertFalse(isRightCalled.get());
        assertEquals(result.get().value().left(), 'a');
        assertFalse(result.get().value().isRight());
    }
}