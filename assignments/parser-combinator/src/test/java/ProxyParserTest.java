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

    @Test
    public void testProxyParser_NestedParenthesesWithDigit_ReturnsInnerValueAndOffset(){
        ProxyParser<String> proxyParser = new ProxyParser<>();

        Parser<String> baseParser = Parsers.digitParser()
                .map(String::valueOf);

        Parser<String> recursiveParser =Parsers.characterParser('(')
                .skipLeft(proxyParser)
                .skipRight(Parsers.characterParser(')'));

        proxyParser.setDelegate(
                recursiveParser
                        .or(baseParser)
                        .map(either->either.isLeft() ? either.left() : either.right())
        );

        var result = proxyParser.apply("((4))", 0);

        assertEquals(result.get().value(), "4");
        assertEquals(result.get().offset(),5);
    }

    @Test
    public void testProxyParser_VariableWhitespaces() {
        ProxyParser<String> proxyParser = new ProxyParser<>();

        // digit
        Parser<String> baseParser = Parsers.digitParser()
                .map(String::valueOf);

        // ['('] digit [')']
        Parser<String> recursiveParser =Parsers.characterParser('(')
                .skipLeft(proxyParser)
                .skipRight(Parsers.characterParser(')'));

        // {ws}
        Parser<List<Character>> wsParser = Parsers.whitespaceParser().zeroOrMore();

        // как в грамматике описать чередование пробелов и скобок?

        // {ws} ['('] digit [')'] {ws}
        var expr = wsParser.skipLeft(proxyParser).skipRight(wsParser);

        // ['('] digit [')'] | digit
        proxyParser.setDelegate(recursiveParser
                .or(expr)
                .map(either->either.isLeft() ? either.left() : either.right()));
        // => java.lang.StackOverflowError

        var result = expr.apply("  (( 4    )  )", 0);

//        assertEquals(result.get().value());
        assertEquals(result.get().offset(), 1);
    }

    }