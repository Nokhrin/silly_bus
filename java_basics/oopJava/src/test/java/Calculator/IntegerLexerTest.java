package Calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerLexerTest {

    @Test
    public void testParsePositiveNumber() {
        IntegerLexer lexer = new IntegerLexer("123");
        ParseResult result = lexer.parseFrom(0);
        assertNotNull(result);
        assertEquals(123, result.getValue().intValue());
        assertEquals(0, result.getStart());
        assertEquals(2, result.getEnd());
    }
}