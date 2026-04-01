package lambda_parser;

import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ListParserTest {
    @Test(description = "число")
    void testIntsNoSpaces() {
        ListParser<Integer> listParser = new ListParser<>(new IntParser(), 0, 10);

        var result = listParser.parse("12345", 0);

        assertTrue(result.isPresent());
        assertEquals(List.of(12345), result.get().value());
        assertEquals(5, result.get().end_offset());
    }

    @Test(description = "операторы")
    void testOperators() {
        ListParser<Integer> listParser = new ListParser<>(new BinaryOperatorParser(), 0, 10);

        var result = listParser.parse("+-*/", 0);

        assertTrue(result.isPresent());
        assertEquals(List.of(
                BinaryOperatorParser.Operation.ADD,
                BinaryOperatorParser.Operation.SUB,
                BinaryOperatorParser.Operation.MUL,
                BinaryOperatorParser.Operation.DIV
                ), result.get().value());
        assertEquals(4, result.get().end_offset());
    }

    @Test(description = "пробелы")
    void testWhitespaces() {
        ListParser<Integer> listParser = new ListParser<>(new WhitespaceParser(), 0, 10);

        var result = listParser.parse(" \t\n\r", 0);

        assertTrue(result.isPresent());
        assertEquals(List.of(" \t\n\r"), result.get().value());
        assertEquals(4, result.get().end_offset());
    }

}
