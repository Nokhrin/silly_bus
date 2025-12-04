package CalcLexer;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExpressionTest {

    @Test
    public void testNumValueEvaluate() {
        Expression expr = new NumValue(42.0);
        Assert.assertEquals(expr.evaluate(), 42.0);
    }

    @Test
    public void testBinOpAdd() {
        Expression expr = new BinOp(1, Parsers.Operation.ADD, 2);
        Assert.assertEquals(expr.evaluate(), 3.0);
    }

    @Test
    public void testBinOpMul() {
        Expression expr = new BinOp(3, Parsers.Operation.MUL, 4);
        Assert.assertEquals(expr.evaluate(), 12.0);
    }

    @Test
    public void testBinOpDiv() {
        Expression expr = new BinOp(10, Parsers.Operation.DIV, 2);
        Assert.assertEquals(expr.evaluate(), 5.0);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        Expression expr = new BinOp(1, Parsers.Operation.DIV, 0);
        expr.evaluate(); // Должно выбросить исключение
    }
}