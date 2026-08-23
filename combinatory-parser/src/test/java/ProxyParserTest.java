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
    public void testProxyParser_NestedParenthesesWithDigit_ReturnsInnerValueAndOffset() {
        ProxyParser<String> proxyParser = new ProxyParser<>();

        Parser<String> baseParser = Parsers.digitParser()
                .map(String::valueOf);

        Parser<String> recursiveParser = Parsers.characterParser('(')
                .skipLeft(proxyParser)
                .skipRight(Parsers.characterParser(')'));

        proxyParser.setDelegate(
                recursiveParser
                        .or(baseParser)
                        .map(either -> either.isLeft() ? either.left() : either.right())
        );

        var result = proxyParser.apply("((4))", 0);

        assertEquals(result.get().value(), "4");
        assertEquals(result.get().offset(), 5);
    }

    @Test
    public void testProxyParser_VariableWhitespaces() {
        // {ws}
        Parser<List<Character>> ws = Parsers.whitespaceParser().zeroOrMore();

        // (
        Parser<Character> leftPar = Parsers.characterParser('(');

        // )
        Parser<Character> rightPar = Parsers.characterParser(')');

        // digit
        Parser<String> digit = Parsers.digitParser().map(String::valueOf);

        ProxyParser<String> expr = new ProxyParser<>();

        // ( {ws} expr {ws} )
        Parser<String> exprInPar = leftPar
                .skipRight(ws)
                .skipLeft(expr)
                .skipRight(ws)
                .skipRight(rightPar);

        // {ws} ( digit | ( {ws} expr {ws} ) ) {ws}
        expr.setDelegate(ws.skipLeft(digit.or(exprInPar))
                .map(either -> either.isLeft() ? either.left() : either.right())
                .skipRight(ws));

                var result = expr.apply("  (( 4    )  )", 0);

        assertEquals(result.get().value(), "4");
        assertEquals(result.get().offset(), 14);
    }

}