package com.nokhrin.expr;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @DataProvider(name = "expressions")
    public Object[][] provideData() {
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

    @Test(dataProvider = "expressions")
    public void testValidExpr(String expression, int expected) {
        assertEquals(calculator.parse(expression), expected);
    }


}
