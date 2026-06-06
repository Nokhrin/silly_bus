package com.nokhrin.expr;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @DataProvider(name = "expressions")
    public Object[][] provideExpressions() {
        return new Object[][]{
                {"1 + 2 + 3", 6},
                {"10 - 2 - 3", 5},
                {"2 * 3 + 4", 10},
                {"10 / 2 * 3", 15},
                {"(1 + 2) * 3", 9},
                {"10 + 2 * 3 - 4 / 2", 14},
                {"100", 100}
        };
    }

    @DataProvider(name = "operators")
    public Object[][] provideOperators() {
        return new Object[][]{
                {"1 + 2", 3},
                {"5 - 2", 3},
                {"3 * 4", 12},
                {"10 / 2", 5},

//                {"2 ^ 3", 8},TODO: 2026-06-04

//                {"-5", -5},
                {"+5", 5},

//                {"5!", 120},

                {"|-5|", 5},

                {"(2 + 3) * 2", 10},
//                {"() + 1", 1},

                {"x = 5", 5},
        };
    }
    @Test(dataProvider = "expressions")
    public void testValidExpr(String expression, int expected) {
        assertEquals(calculator.parse(expression), expected);
    }

    @Test(dataProvider = "operators")
    public void testValidOperators(String expression, int expected) {
        assertEquals(calculator.parse(expression), expected);
    }


}
