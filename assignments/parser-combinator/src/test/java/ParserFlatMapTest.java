import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Spliterator;

import static org.testng.Assert.*;

public class ParserFlatMapTest {

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
}