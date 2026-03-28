package lambda_parser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class BinaryOperatorParserTest {
    private final Parser<String> boParser = new BinaryOperatorParser();

    @Test(description = "Символ +")
    void testPlusOnly() {
        var result = boParser.parse("+", 0);
        assertTrue(result.isPresent());
        assertEquals(BinaryOperatorParser.Operation.ADD, result.get().value());
        assertEquals(1, result.get().end_offset());
    }

    @Test(description = "Символ -")
    void testMinusOnly() {
        var result = boParser.parse("-", 0);
        assertTrue(result.isPresent());
        assertEquals(BinaryOperatorParser.Operation.SUB, result.get().value());
        assertEquals(1, result.get().end_offset());
    }


    @Test(description = "Символ *")
    void testMultOnly() {
        var result = boParser.parse("*", 0);
        assertTrue(result.isPresent());
        assertEquals(BinaryOperatorParser.Operation.MUL, result.get().value());
        assertEquals(1, result.get().end_offset());
    }

    @Test(description = "Символ /")
    void testDivOnly() {
        var result = boParser.parse("/", 0);
        assertTrue(result.isPresent());
        assertEquals(BinaryOperatorParser.Operation.DIV, result.get().value());
        assertEquals(1, result.get().end_offset());
    }


    @Test(description = "Допустимые Операторы")
    void testValidOps() {
        assertEquals(BinaryOperatorParser.Operation.ADD, boParser.parse("+", 0).get().value());
        assertEquals(BinaryOperatorParser.Operation.MUL, boParser.parse("*", 0).get().value());
        assertEquals(BinaryOperatorParser.Operation.SUB, boParser.parse("-", 0).get().value());
        assertEquals(BinaryOperatorParser.Operation.DIV, boParser.parse("/", 0).get().value());
    }


    @Test(description = "Смещение выполняется")
    void testOffsetIncr() {
        var result = boParser.parse("/test123", 0);
        assertTrue(result.isPresent());
        assertEquals(BinaryOperatorParser.Operation.DIV, result.get().value());
        assertEquals(1, result.get().end_offset());
    }

    @Test(description = "Недопустимый символ")
    void testInvalidChar() {
        var result = boParser.parse("N", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Пустой ввод")
    void testEmptyInput() {
        var result = boParser.parse("", 0);
        assertTrue(result.isEmpty());
    }


}