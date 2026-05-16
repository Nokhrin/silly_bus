import org.testng.annotations.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.*;

public class ParserTest {

    @Test
    public void testFlatMapLeftFailed_MapperNotCalled() {
        Parser<Character> failedParser = (source, offset) -> Optional.empty();
        boolean[] mapperCalled = {false};
        Parser<String> result = failedParser.flatMap(parser -> {
            mapperCalled[0] = true; //ожидаем, что эта инструкция не выполнится
            return Parsers.stringParser("test");
        });
        assertTrue(result.apply("abcd", 0).isEmpty());
        assertFalse(mapperCalled[0]);
    }

    @Test
    public void testLeftSucceed_MapperFailed() {
        Parser<Character> leftParserSuccess = Parsers.characterParser('a');
        Parser<Character> result = leftParserSuccess.flatMap(parser ->
                (source, offset) -> Optional.empty()
        );
        assertTrue(result.apply("ab", 0).isEmpty());
    }

    @Test
    public void testFlatMapOffsetCorrect() {
        Parser<Character> parserA = Parsers.characterParser('a');
        Parser<String> resultParser = parserA.flatMap(characterParsed ->
                (source, offset) -> {
                    if (offset == 1 && source.startsWith("b", offset)) {
                        String matched = source.substring(0, offset + 1);
                        return Optional.of(new Parsed<>(matched, offset + 1));
                    }
                    return Optional.empty();
                });
        Optional<Parsed<String>> result = resultParser.apply("ab", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "ab");
        assertEquals(result.get().offset(), 2);
    }


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
    public void testLeftSuccessRightNotCalled() {
        AtomicBoolean isRightCalled = new AtomicBoolean(false);

        Parser<Character> leftParser = (source, offset) ->
                Optional.of(new Parsed<>('a', offset + 1));
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

    @Test
    public void testPlusTwoParsersMatch() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Tuple<Character, Character>> twoChars = p1.plus(p2);
        Optional<Parsed<Tuple<Character, Character>>> result = twoChars.apply("ab", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().left(), 'a');
        assertEquals(result.get().value().right(), 'b');
        assertEquals(result.get().offset(), 2);
    }

    @Test(description = "Наследование отказа: отказ левого парсера приводит к отказу всей цепочки")
    public void testPlusLeftFailed() {
        Parser<Character> p1 = Parsers.characterParser('a');
        Parser<Character> p2 = Parsers.characterParser('b');
        Parser<Tuple<Character, Character>> twoChars = p1.plus(p2);
        Optional<Parsed<Tuple<Character, Character>>> result = twoChars.apply("bb", 0);

        assertTrue(result.isEmpty());
    }

    @Test(description = "Наследование отказа: успех левого + отказ правого парсера приводит к отказу всей цепочки")
    public void testPlusLeftSucceedsRightFailed() {
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

    @Test(description = "Успех + трансформация + проверка смещения")
    public void testMapTransformSuccess() {
        Parser<Character> characterParser = Parsers.digitParser();
        Parser<Integer> integerParser = characterParser.map(Character::getNumericValue);
        Optional<Parsed<Integer>> result = integerParser.apply("1", 0);

        assertEquals(result.get().value(), 1);

    }

    @Test
    public void testMapParserFailedTransformSkipped() {
        AtomicBoolean isTransformed = new AtomicBoolean(false);
        Parser<Character> characterParser = Parsers.digitParser();
        Parser<Integer> integerParser = characterParser
                .map(parsedChar -> {
                    isTransformed.set(true);
                    return 0;
                });
        Optional<Parsed<Integer>> result = integerParser.apply("a", 0);

        assertTrue(result.isEmpty());
        assertFalse(isTransformed.get());

    }

    @Test
    public void testMapChainTwoDifferentTypes() {
        Parser<Character> characterParser = Parsers.digitParser();
        Parser<String> stringParser = characterParser
                .map(Character::getNumericValue)
                .map(String::valueOf);
        Optional<Parsed<String>> result = stringParser.apply("1", 0);

        assertEquals(result.get().value(), "1");
    }
}
