package calc_parser;

import org.testng.Assert;
import org.testng.annotations.Test;

//region Проверка базовых арифметических операций
@Test(groups = "basic-operations")
public class ExpressionTest {
    //region NumValueTests
    @Test(groups = "unit", description = "Проверка, что числовое значение возвращает само себя")
    public void testNumValueEvaluate() {
        Expression expr = new NumValue(42.0);
        Assert.assertEquals(expr.evaluate(), 42.0, "Числовое выражение должно возвращать своё значение");
    }

    @Test(groups = "unit", description = "Проверка отрицательного числа")
    public void testNegativeNumValue() {
        Expression expr = new NumValue(-5.5);
        Assert.assertEquals(expr.evaluate(), -5.5, "Отрицательное числовое значение должно возвращаться корректно");
    }

    @Test(groups = "unit", description = "Проверка нуля как числового значения")
    public void testZeroNumValue() {
        Expression expr = new NumValue(0.0);
        Assert.assertEquals(expr.evaluate(), 0.0, "Числовое значение 0.0 должно возвращаться корректно");
    }
    //endregion

    //region BinaryExpressionTests
    @Test(groups = "unit", description = "Проверка сложения двух положительных чисел")
    public void testAdditionPositive() {
        Expression left = new NumValue(10.0);
        Expression right = new NumValue(5.0);
        Expression expr = new BinaryExpression(left, Parser.Operation.ADD, right);
        Assert.assertEquals(expr.evaluate(), 15.0, "Сложение 10 + 5 должно дать 15");
    }

    @Test(groups = "unit", description = "Проверка вычитания")
    public void testSubtraction() {
        Expression left = new NumValue(10.0);
        Expression right = new NumValue(3.0);
        Expression expr = new BinaryExpression(left, Parser.Operation.SUB, right);
        Assert.assertEquals(expr.evaluate(), 7.0, "Вычитание 10 - 3 должно дать 7");
    }

    @Test(groups = "unit", description = "Проверка умножения")
    public void testMultiplication() {
        Expression left = new NumValue(4.0);
        Expression right = new NumValue(5.0);
        Expression expr = new BinaryExpression(left, Parser.Operation.MUL, right);
        Assert.assertEquals(expr.evaluate(), 20.0, "Умножение 4 * 5 должно дать 20");
    }

    @Test(groups = "unit", description = "Проверка деления")
    public void testDivision() {
        Expression left = new NumValue(20.0);
        Expression right = new NumValue(4.0);
        Expression expr = new BinaryExpression(left, Parser.Operation.DIV, right);
        Assert.assertEquals(expr.evaluate(), 5.0, "Деление 20 / 4 должно дать 5");
    }

    @Test(groups = "unit", description = "Проверка исключения при делении на ноль")
    public void testDivisionByZero() {
        Expression left = new NumValue(10.0);
        Expression right = new NumValue(0.0);
        Expression expr = new BinaryExpression(left, Parser.Operation.DIV, right);

        try {
            expr.evaluate();
            Assert.fail("Должно быть выброшено ArithmeticException при делении на ноль");
        } catch (ArithmeticException e) {
            Assert.assertEquals(e.getMessage(), "Деление на ноль", "Сообщение об ошибке должно быть точным");
        }
    }
    //endregion

    //region NestedExpressionsTests
    @Test(groups = "integration", description = "Проверка вложенного выражения: (1 + 2) * 3")
    public void testNestedExpressionAdditionAndMultiplication() {
        Expression inner = new BinaryExpression(
                new NumValue(1.0),
                Parser.Operation.ADD,
                new NumValue(2.0)
        );
        Expression expr = new BinaryExpression(
                inner,
                Parser.Operation.MUL,
                new NumValue(3.0)
        );
        Assert.assertEquals(expr.evaluate(), 9.0, "Вычисление (1 + 2) * 3 должно дать 9");
    }

    @Test(groups = "integration", description = "Проверка сложного выражения: 10 / (2 + 3) * 4")
    public void testComplexExpression() {
        Expression right = new BinaryExpression(
                new NumValue(2.0),
                Parser.Operation.ADD,
                new NumValue(3.0)
        );
        Expression inner = new BinaryExpression(
                new NumValue(10.0),
                Parser.Operation.DIV,
                right
        );
        Expression expr = new BinaryExpression(
                inner,
                Parser.Operation.MUL,
                new NumValue(4.0)
        );
        Assert.assertEquals(expr.evaluate(), 8.0, "Вычисление 10 / (2 + 3) * 4 должно дать 8");
    }
    //endregion
}