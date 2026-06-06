package com.nokhrin.expr;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @DataProvider(name = "expressions")
    public Object[][] provideOperators() {
        return new Object[][]{
                // арифметика и приоритеты
                {"1 + 2", new ExprValue.IntValue(3)},
                {"10 - 2 - 3", new ExprValue.IntValue(5)},
                {"2 * 3 + 4", new ExprValue.IntValue(10)},
                {"10 / 2 * 3", new ExprValue.IntValue(15)},
                {"(1 + 2) * 3", new ExprValue.IntValue(9)},
                {"100", new ExprValue.IntValue(100)},

                // Унарные операторы
                {"-1 + 2", new ExprValue.IntValue(1)},
                {"+5", new ExprValue.IntValue(5)},

                // Дробные числа и вывод типов
                {"1.5 + 2.5", new ExprValue.DoubleValue(4.0)},
                {"5 / 2", new ExprValue.DoubleValue(2.5)},

                {"2 ^ 3", new ExprValue.IntValue(8)},
                {"5!", new ExprValue.IntValue(120)},
                {"|-5|", new ExprValue.IntValue(5)},

                // Переменные
                {"x = 5", new ExprValue.IntValue(5)},
        };
    }

    @Test(groups = "integration", dataProvider = "expressions")
    public void testValidExpr(String expression, ExprValue expected) {
        assertEquals(calculator.parse(expression), expected);
    }

}
