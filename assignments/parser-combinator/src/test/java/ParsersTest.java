import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ParsersTest {

    //region ===== characterParser =====
    @Test
    public void testCharacterParser_ExactMatch_ReturnsValueAndOffset() {

        Parser<Character> parser = Parsers.characterParser('a');
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertEquals(result.get().value(), 'a');
        assertEquals(result.get().offset(), 1);
    }

    @Test
    public void testCharacterParser_DifferentChar_ReturnsEmpty() {

        Parser<Character> parser = Parsers.characterParser('b');
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testCharacterParser_EmptyInput_ReturnsEmpty() {

        Parser<Character> parser = Parsers.characterParser('a');
        Optional<Parsed<Character>> result = parser.apply("", 0);

        assertTrue(result.isEmpty());
    }
    //endregion

    //region ===== digitParser =====
    @Test
    public void testDigitParser_ValidDigit_ReturnsValue() {

        Parser<Character> parser = Parsers.digitParser();
        Optional<Parsed<Character>> result = parser.apply("1", 0);

        assertEquals(result.get().value(), '1');
        assertEquals(result.get().offset(), 1);
    }

    @Test
    public void testDigitParser_LetterInput_ReturnsEmpty() {

        Parser<Character> parser = Parsers.digitParser();
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertTrue(result.isEmpty());
    }

    //endregion

    //region ===== stringParser =====
    @Test
    public void testStringParser_FullMatch_ReturnsPattern() {
        Parser<String> parser = Parsers.stringParser("hello");
        Optional<Parsed<String>> result = parser.apply("hello", 0);

        assertEquals(result.get().value(), "hello");
        assertEquals(result.get().offset(), 5);
    }

    @Test
    public void testStringParser_MatchAtOffset_ReturnsValue() {
        Parser<String> parser = Parsers.stringParser("world");
        Optional<Parsed<String>> result = parser.apply("hello, world abc", 7);

        assertEquals(result.get().value(), "world");
        assertEquals(result.get().offset(), 12);
    }

    @Test
    public void testStringParser_PatternLongerThanInput_ReturnsEmpty() {
        Parser<String> parser = Parsers.stringParser("hello");
        Optional<Parsed<String>> result = parser.apply("hell", 0);

        assertTrue(result.isEmpty());
    }
    //endregion

    //region ===== whitespaceParser =====
    @Test
    public void testWhitespaceParser_SpaceChar_ReturnsValue() {
        Parser<Character> parser = Parsers.whitespaceParser();
        Optional<Parsed<Character>> result = parser.apply(" ", 0);

        assertEquals(result.get().value(), ' ');
        assertEquals(result.get().offset(), 1);
    }
    //endregion

    //region ===== eof =====
    @Test(groups = "eof")
    public void testEof_AfterSingleChar_ReturnsSuccess(){
        Parser<Tuple<Character, String>>parserWithEof= Parsers.characterParser('a').plus(Parsers.eof());
        Optional<Parsed<Tuple<Character, String>>> result=parserWithEof.apply("a", 0);

        assertEquals(result.get().value().left(), 'a');
        assertEquals(result.get().offset(), 1);
    }

    @Test(groups = "eof")
    public void testEof_WithTrailingInput_ReturnsEmpty() {
        Parser<Tuple<Character, String>>parserWithEof= Parsers.characterParser('a').plus(Parsers.eof());
        Optional<Parsed<Tuple<Character, String>>> result=parserWithEof.apply("ab", 0);

        assertTrue(result.isEmpty());
    }

    @Test(groups = "eof")
    public void testEof_ValidatesFullInputConsumption_ReturnsParsedDigit(){
        var chainedParser=Parsers.digitParser().skipRight(Parsers.whitespaceParser().zeroOrMore()).plus(Parsers.eof());
        var result =chainedParser.apply("9    ", 0);

        assertEquals(result.get().value().left(), '9');
        assertEquals(result.get().offset(), 5);
    }
    //endregion

    //region ===== whitespaces =====
    @Test(groups = "whitespaces")
    public void testWhitespaces_Empty_ZeroOffset(){
        Optional<Parsed<String>> result = Parsers.whitespaces().apply("", 0);

        assertEquals(result.get().value(), "");
        assertEquals(result.get().offset(), 0);
    }

    @Test(groups = "whitespaces")
    public void testWhitespaces_StopAtNonWhitespace(){
        Optional<Parsed<String>> result = Parsers.whitespaces().apply("  a", 0);

        assertEquals(result.get().value(), "");
        assertEquals(result.get().offset(), 2);
    }

    @Test(groups = "whitespaces")
    public void testWhitespaces_MixedWhitespacesOnly_OffsetEqualsLength(){
        Optional<Parsed<String>> result = Parsers.whitespaces().apply(" \t\n\n\n\r \f", 0);

        assertEquals(result.get().value(), "");
        assertEquals(result.get().offset(), 8);

    }

    //endregion


}