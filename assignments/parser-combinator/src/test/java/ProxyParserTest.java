import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ProxyParserTest {
    @Test
    public void testDelegate_OneValue_Parsed() {
        ProxyParser<Character> proxyParser = new ProxyParser<>();
        proxyParser.setDelegate(Parsers.characterParser('a'));
        Optional<Parsed<Character>> result = proxyParser.apply("a", 0);

        assertEquals(result.get().value(), 'a');
        assertEquals(result.get().offset(), 1);
    }

    @Test
    public void testInit_ProxyNotPassed_ExceptionThrown() {
        ProxyParser<Character> proxyParser = new ProxyParser<>();

        assertThrows(IllegalStateException.class, () -> {
            proxyParser.apply("test", 0);
        });
    }

    @Test
    public void testBalanced_ValueInParenthesis_Parsed() {
        ProxyParser<String> proxyParser = new ProxyParser<>();

        Parser<String> digitParser = Parsers.digitParser()
                .map(String::valueOf);

        Parser<String> ignoreParenthesis = Parsers.characterParser('(')
                .skipLeft(proxyParser)
                .skipRight(Parsers.characterParser(')'));

        proxyParser.setDelegate(
                ignoreParenthesis.or(digitParser)
                        .map(either -> either.isLeft() ? either.left() : either.right())
        );

        Optional<Parsed<String>> result = proxyParser.apply("(1)", 0);

        assertEquals(result.get().value(), "1");
        assertEquals(result.get().offset(), 3);


    }
}