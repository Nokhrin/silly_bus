package com.nokhrin.interpreter.common;

import net.jqwik.api.ForAll;
import net.jqwik.api.constraints.IntRange;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ArithmeticOperationsTest {

    @DataProvider(name = "additionCases")
    public Object[][] provideAdditionCases() {
        return new Object[][]{
                {"int + int", new IntValue(5), new IntValue(3), new IntValue(8)},
                {"int + int negative", new IntValue(-5), new IntValue(3), new IntValue(-2)},
                {"int + int zero", new IntValue(0), new IntValue(0), new IntValue(0)},
                {"int + double", new IntValue(5), new DoubleValue(3.5), new DoubleValue(8.5)},
                {"double + int", new DoubleValue(5.5), new IntValue(3), new DoubleValue(8.5)},
                {"double + double", new DoubleValue(5.5), new DoubleValue(3.5), new DoubleValue(9.0)},
        };
    }

    @Test(dataProvider = "additionCases")
    public void add_validOperands_expectedResult(String description, ExprValue left, ExprValue right, ExprValue expected) {
        assertEquals(ArithmeticOperations.add(left, right), expected, description);
    }

    @DataProvider(name = "divisionCases")
    public Object[][] provideDivisionCases() {
        return new Object[][]{
                {"int / int exact", new IntValue(10), new IntValue(2), new IntValue(5)},
                {"int / int fractional", new IntValue(5), new IntValue(2), new DoubleValue(2.5)},
                {"double / int", new DoubleValue(5.5), new IntValue(2), new DoubleValue(2.75)},
                {"int / 1", new IntValue(42), new IntValue(1), new IntValue(42)},
                {"int / -1", new IntValue(42), new IntValue(-1), new IntValue(-42)},
        };
    }

    @Test(dataProvider = "divisionCases")
    public void div_validOperands_expectedResult(String description, ExprValue left, ExprValue right, ExprValue expected) {
        assertEquals(ArithmeticOperations.div(left, right), expected, description);
    }

    @DataProvider(name = "negationCases")
    public Object[][] provideNegationCases() {
        return new Object[][]{
                {"int positive", new IntValue(5), new IntValue(-5)},
                {"int negative", new IntValue(-5), new IntValue(5)},
                {"int zero", new IntValue(0), new IntValue(0)},
                {"double positive", new DoubleValue(5.5), new DoubleValue(-5.5)},
                {"double negative", new DoubleValue(-5.5), new DoubleValue(5.5)},
        };
    }

    @Test(dataProvider = "negationCases")
    public void neg_validOperand_expectedResult(String description, ExprValue operand, ExprValue expected) {
        assertEquals(ArithmeticOperations.neg(operand), expected, description);
    }

    @Test
    void div_byZero_throwsArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> ArithmeticOperations.div(new IntValue(1), new IntValue(0)));
    }

    @Test(enabled = false, description = "will be implemented in sprint 3")
    void add_intMaxPlusOne_returnsDouble(){
        ExprValue result = ArithmeticOperations.add(
                new IntValue(Integer.MAX_VALUE),
                new IntValue(1)
        );
        assertTrue(result instanceof DoubleValue);
    }
}