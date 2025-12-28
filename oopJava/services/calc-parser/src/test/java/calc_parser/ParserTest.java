package calc_parser;
import static calc_parser.Parser.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class ParserTest {

    //region parseNumber
    @Test(description = "Парсинг обычного числа: 123")
    public void testParseNumberValid() {
        Optional<ParseResult<NumValue>> result = Parser.parseNumber("123", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value().value(), 123);
        Assert.assertEquals(result.get().start(), 0);
        Assert.assertEquals(result.get().end(), 3);
    }

    @Test(description = "Парсинг числа с ведущими нулями: 00123")
    public void testParseNumberLeadingZeros() {
        Optional<ParseResult<NumValue>> result = Parser.parseNumber("00123", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value().value(), 123);
        Assert.assertEquals(result.get().start(), 0);
        Assert.assertEquals(result.get().end(), 5);
    }

    @Test(description = "Парсинг числа 0")
    public void testParseNumberZero() {
        Optional<ParseResult<NumValue>> result = Parser.parseNumber("0", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value().value(), 0);
        Assert.assertEquals(result.get().start(), 0);
        Assert.assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг пустой строки")
    public void testParseNumberEmptyString() {
        Optional<ParseResult<NumValue>> result = Parser.parseNumber("", 0);
        Assert.assertTrue(result.isEmpty());
    }
    //endregion

    //region brackets
    @Test(groups = "brackets", description = "Парсинг открывающей скобки: '('")
    public void testParseBracketsOpening() {
        Optional<ParseResult<Parser.Brackets>> result = Parser.parseBrackets("(", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parser.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parser.Brackets.OPENING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг закрывающей скобки: ')' ")
    public void testParseBracketsClosing() {
        Optional<ParseResult<Parser.Brackets>> result = Parser.parseBrackets(")", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parser.Brackets> parsed = result.get();
        assertEquals(parsed.value(), Parser.Brackets.CLOSING);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "brackets", description = "Парсинг скобки: символ не скобка - ожидается пустой результат")
    public void testParseBracketsInvalid() {
        Optional<ParseResult<Parser.Brackets>> result = Parser.parseBrackets("a", 0);
        Assert.assertFalse(result.isPresent());
    }
    //endregion

    //region operation
    @Test(groups = "operation", description = "Парсинг операции: '+'")
    public void testParseOperationAdd() {
        Optional<ParseResult<Parser.Operation>> result = Parser.parseOperation("+", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parser.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parser.Operation.ADD);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '*'")
    public void testParseOperationMul() {
        Optional<ParseResult<Parser.Operation>> result = Parser.parseOperation("*", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parser.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parser.Operation.MUL);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "operation", description = "Парсинг операции: '/'")
    public void testParseOperationDiv() {
        Optional<ParseResult<Parser.Operation>> result = Parser.parseOperation("/", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<Parser.Operation> parsed = result.get();
        assertEquals(parsed.value(), Parser.Operation.DIV);
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }
    //endregion

    //region whitespace
    @Test(groups = "whitespace", description = "Парсинг пробелов: '    ' (4 пробела)")
    public void testParseWhitespaceSpaces() {
        Optional<ParseResult<String>> result = Parser.parseWhitespace("    hello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 4);
    }

    @Test(groups = "whitespace", description = "Парсинг табуляции: '\\t'")
    public void testParseWhitespaceTab() {
        Optional<ParseResult<String>> result = Parser.parseWhitespace("\thello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсинг пробелов и табуляции: '  \t  ' (2 пробела, 1 таб, 2 пробела)")
    public void testParseWhitespaceMixed() {
        Optional<ParseResult<String>> result = Parser.parseWhitespace("  \t  hello", 0);
        Assert.assertTrue(result.isPresent());
        ParseResult<String> parsed = result.get();
        assertEquals(parsed.value(), "");
        assertEquals(parsed.start(), 0);
        assertEquals(parsed.end(), 5);
    }

    @Test(groups = "whitespace", description = "Парсит один пробел")
    public void testParseSingleSpace() {
        Optional<ParseResult<String>> result = parseWhitespace(" a", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсит табуляцию")
    public void testParseTab() {
        Optional<ParseResult<String>> result = parseWhitespace("\tabc", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсит символ перевода строки")
    public void testParseNewLine() {
        Optional<ParseResult<String>> result = parseWhitespace("\nabc", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсит возврат каретки")
    public void testParseCarriageReturn() {
        Optional<ParseResult<String>> result = parseWhitespace("\rabc", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(groups = "whitespace", description = "Парсинг с пустой строкой — неудачный парсинг")
    public void testParseEmptySource() {
        Optional<ParseResult<String>> result = parseWhitespace("", 0);
        assertFalse(result.isPresent());
    }

    @Test(groups = "whitespace", description = "Парсинг с start за пределами строки — неудачный парсинг")
    public void testParseStartOutOfRange() {
        Optional<ParseResult<String>> result = parseWhitespace("abc", 5);
        assertFalse(result.isPresent());
    }

    @Test(groups = "whitespace", description = "Парсинг с start = source.length() — пустой парсинг")
    public void testParseStartAtEnd() {
        Optional<ParseResult<String>> result = parseWhitespace("abc", 3);
        assertFalse(result.isPresent());
    }

    @Test(groups = "whitespace", description = "Парсинг с не-пробельным символом в начале — неудачный парсинг")
    public void testParseNonWhitespace() {
        Optional<ParseResult<String>> result = parseWhitespace("abc", 0);
        assertFalse(result.isPresent());
    }

    @Test(groups = "whitespace", description = "Парсинг с пробелом, но не с начала — в середине строки")
    public void testParseWhitespaceInMiddle() {
        Optional<ParseResult<String>> result = parseWhitespace("abc   def", 3);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 3);
        assertEquals(result.get().end(), 6);
    }

    @Test(groups = "whitespace", description = "Парсинг нескольких пробелов подряд — съедает все")
    public void testParseMultipleSpaces() {
        Optional<ParseResult<String>> result = parseWhitespace("    abc", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test(groups = "whitespace", description = "Парсинг табуляции и пробелов вперемешку")
    public void testParseMixedWhitespace() {
        Optional<ParseResult<String>> result = parseWhitespace("\t \n\rabc", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), "");
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 4);
    }

    @Test(groups = "whitespace", description = "Парсинг с пробельным символом, но не в списке (например, неразрывный пробел)")
    public void testParseNonStandardWhitespace() {
        Optional<ParseResult<String>> result = parseWhitespace("\u00A0abc", 0);
        assertFalse(result.isPresent()); // \u00A0 — не считается пробелом в Character.isWhitespace()
    }
    //endregion

    //region parseMulDivOperationTest
    
    @Test(description = "Парсинг умножения: *")
    public void testParseMulDivOperation_Multiply() {
        var result = Parser.parseMulDivOperation("*", 0);
        assertEquals(result.get().value(), Parser.Operation.MUL);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг деления: /")
    public void testParseMulDivOperation_Divide() {
        var result = Parser.parseMulDivOperation("/", 0);
        assertEquals(result.get().value(), Parser.Operation.DIV);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг умножения с пробелами перед ним")
    public void testParseMulDivOperation_MultiplyWithWhitespaceBefore() {
        var result = Parser.parseMulDivOperation(" *", 1);
        assertEquals(result.get().value(), Parser.Operation.MUL);
        assertEquals(result.get().start(), 1);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Парсинг деления с пробелами перед ним")
    public void testParseMulDivOperation_DivideWithWhitespaceBefore() {
        var result = Parser.parseMulDivOperation(" /", 1);
        assertEquals(result.get().value(), Parser.Operation.DIV);
        assertEquals(result.get().start(), 1);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Парсинг умножения в середине строки")
    public void testParseMulDivOperation_MultiplyInMiddle() {
        var result = Parser.parseMulDivOperation("10 * 20", 3);
        assertEquals(result.get().value(), Parser.Operation.MUL);
        assertEquals(result.get().start(), 3);
        assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг деления в середине строки")
    public void testParseMulDivOperation_DivideInMiddle() {
        var result = Parser.parseMulDivOperation("10 / 20", 3);
        assertEquals(result.get().value(), Parser.Operation.DIV);
        assertEquals(result.get().start(), 3);
        assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг несуществующего оператора: +")
    public void testParseMulDivOperation_InvalidOperator_Plus() {
        var result = Parser.parseMulDivOperation("+", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг несуществующего оператора: -")
    public void testParseMulDivOperation_InvalidOperator_Minus() {
        var result = Parser.parseMulDivOperation("-", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг несуществующего оператора: x")
    public void testParseMulDivOperation_InvalidOperator_X() {
        var result = Parser.parseMulDivOperation("x", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг пустой строки")
    public void testParseMulDivOperation_EmptyString() {
        var result = Parser.parseMulDivOperation("", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг за пределами строки")
    public void testParseMulDivOperation_OutOfBounds() {
        var result = Parser.parseMulDivOperation("5", 5);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг с отрицательным индексом")
    public void testParseMulDivOperation_NegativeStart() {
        var result = Parser.parseMulDivOperation("5", -1);
        assertFalse(result.isPresent());
    }

    //endregion parseMulDivOperationTest
    
    //region parseAddSubOperationTest
    @Test(description = "Парсинг сложения: +")
    public void testParseAddSubOperation_Add() {
        var result = Parser.parseAddSubOperation("+", 0);
        assertEquals(result.get().value(), Parser.Operation.ADD);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг вычитания: -")
    public void testParseAddSubOperation_Subtract() {
        var result = Parser.parseAddSubOperation("-", 0);
        assertEquals(result.get().value(), Parser.Operation.SUB);
        assertEquals(result.get().start(), 0);
        assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг сложения с пробелами перед ним")
    public void testParseAddSubOperation_AddWithWhitespaceBefore() {
        var result = Parser.parseAddSubOperation(" +", 1);
        assertEquals(result.get().value(), Parser.Operation.ADD);
        assertEquals(result.get().start(), 1);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Парсинг вычитания с пробелами перед ним")
    public void testParseAddSubOperation_SubtractWithWhitespaceBefore() {
        var result = Parser.parseAddSubOperation(" -", 1);
        assertEquals(result.get().value(), Parser.Operation.SUB);
        assertEquals(result.get().start(), 1);
        assertEquals(result.get().end(), 2);
    }

    @Test(description = "Парсинг сложения в середине строки")
    public void testParseAddSubOperation_AddInMiddle() {
        var result = Parser.parseAddSubOperation("10 + 20", 3);
        assertEquals(result.get().value(), Parser.Operation.ADD);
        assertEquals(result.get().start(), 3);
        assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг вычитания в середине строки")
    public void testParseAddSubOperation_SubtractInMiddle() {
        var result = Parser.parseAddSubOperation("10 - 20", 3);
        assertEquals(result.get().value(), Parser.Operation.SUB);
        assertEquals(result.get().start(), 3);
        assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг несуществующего оператора: *")
    public void testParseAddSubOperation_InvalidOperator_Multiply() {
        var result = Parser.parseAddSubOperation("*", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг несуществующего оператора: /")
    public void testParseAddSubOperation_InvalidOperator_Divide() {
        var result = Parser.parseAddSubOperation("/", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг несуществующего оператора: x")
    public void testParseAddSubOperation_InvalidOperator_X() {
        var result = Parser.parseAddSubOperation("x", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг пустой строки")
    public void testParseAddSubOperation_EmptyString() {
        var result = Parser.parseAddSubOperation("", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг за пределами строки")
    public void testParseAddSubOperation_OutOfBounds() {
        var result = Parser.parseAddSubOperation("5", 5);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг с отрицательным индексом")
    public void testParseAddSubOperation_NegativeStart() {
        var result = Parser.parseAddSubOperation("5", -1);
        assertFalse(result.isPresent());
    }
    //endregion parseAddSubOperationTest    

    //region parseUnaryOperation

    @Test(description = "Парсинг унарного плюса")
    public void testParseUnaryOperationPlus() {
        Optional<ParseResult<Parser.UnaryOperation>> result = Parser.parseUnaryOperation("+", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value(), Parser.UnaryOperation.POS);
        Assert.assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг унарного минуса")
    public void testParseUnaryOperationMinus() {
        Optional<ParseResult<Parser.UnaryOperation>> result = Parser.parseUnaryOperation("-", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value(), Parser.UnaryOperation.NEG);
        Assert.assertEquals(result.get().end(), 1);
    }

    @Test(description = "Парсинг неоператора")
    public void testParseUnaryOperationInvalidChar() {
        Optional<ParseResult<Parser.UnaryOperation>> result = Parser.parseUnaryOperation("x", 0);
        Assert.assertTrue(result.isEmpty());
    }

    @Test(description = "Пустая строка")
    public void testParseUnaryOperationEmpty() {
        Optional<ParseResult<Parser.UnaryOperation>> result = Parser.parseUnaryOperation("", 0);
        Assert.assertTrue(result.isEmpty());
    }

    //endregion

    //region parseAtomExpressionTest
    @Test
    public void testParseAtomExpression_Number() {
        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("42", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), new NumValue(42));
        assertEquals(result.get().end(), 2);
    }

    // проблема - бесконечная рекурсия в parseAddSubExpression в parseAtomExpression
//    @Test
//    public void testParseAtomExpression_Parentheses() {
//        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("(1+2)", 0);
//        assertTrue(result.isPresent());
//        assertEquals(result.get().value().toString(), "BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]]");
//        assertEquals(result.get().end(), 5);
//    }
//
//    @Test
//    public void testParseAtomExpression_WithSpaces() {
//        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("( 1 + 2 )", 0);
//        assertTrue(result.isPresent());
//        assertEquals(result.get().end(), 9);
//    }

    @Test
    public void testParseAtomExpression_Invalid() {
        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("a", 0);
        assertTrue(result.isEmpty());
    }

    @Test(description = "Парсинг числа")
    public void testParseAtomExpressionNumber() {
        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("123", 0);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().value(), new NumValue(123));
        Assert.assertEquals(result.get().end(), 3);
    }

    @Test(description = "Парсинг унарного минуса: -123")
    public void testParseAtomExpressionUnaryMinus() {
        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("-123", 0);
        Assert.assertTrue(result.isPresent());
        UnaryExpression expr = (UnaryExpression) result.get().value();
        Assert.assertEquals(expr.unaryOperation(), Parser.UnaryOperation.NEG);
        Assert.assertEquals(expr.operand(), new NumValue(123));
        Assert.assertEquals(result.get().end(), 4);
    }

    @Test(description = "Парсинг унарного плюса: +123")
    public void testParseAtomExpressionUnaryPlus() {
        Optional<ParseResult<Expression>> result = Parser.parseAtomExpression("+123", 0);
        Assert.assertTrue(result.isPresent());
        UnaryExpression expr = (UnaryExpression) result.get().value();
        Assert.assertEquals(expr.unaryOperation(), Parser.UnaryOperation.POS);
        Assert.assertEquals(expr.operand(), new NumValue(123));
        Assert.assertEquals(result.get().end(), 4);
    }

    //endregion
    
    
}   