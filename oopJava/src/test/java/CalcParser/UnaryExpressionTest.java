package CalcParser;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UnaryExpressionTest {
    //region UnaryExpression

    @Test(description = "Унарный плюс: +123 = 123")
    public void testUnaryExpressionPos() {
        Expression expr = new UnaryExpression(Parser.UnaryOperation.POS, new NumValue(123));
        Assert.assertEquals(expr.evaluate(), 123.0, 0.0001);
    }

    @Test(description = "Унарный минус: -123 = -123")
    public void testUnaryExpressionNeg() {
        Expression expr = new UnaryExpression(Parser.UnaryOperation.NEG, new NumValue(123));
        Assert.assertEquals(expr.evaluate(), -123.0, 0.0001);
    }

    @Test(description = "Вложенный унарный минус: +(-123) = -123")
    public void testUnaryExpressionNestedNeg() {
        Expression inner = new UnaryExpression(Parser.UnaryOperation.NEG, new NumValue(123));
        Expression outer = new UnaryExpression(Parser.UnaryOperation.POS, inner);
        Assert.assertEquals(outer.evaluate(), -123.0, 0.0001);
    }

    @Test(description = "Вложенный унарный плюс: +(+123) = 123")
    public void testUnaryExpressionNestedPos() {
        Expression inner = new UnaryExpression(Parser.UnaryOperation.POS, new NumValue(123));
        Expression outer = new UnaryExpression(Parser.UnaryOperation.POS, inner);
        Assert.assertEquals(outer.evaluate(), 123.0, 0.0001);
    }

    //endregion

}