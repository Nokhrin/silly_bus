import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class CalculatorTest {
    private static Calculator calculator = new Calculator();

    @Test
    public void testCalculator_SimpleSum() {


        assertEquals(calculator.calculate("1+2-3"), 0.0);
        assertEquals(calculator.calculate("11+22-33"), 0.0);
    }

    @Test
    public void testCalculator_DivisorZero_ThrowsException() {

        assertThrows(ArithmeticException.class, () -> {
            calculator.calculate("1/0");
        });
    }

    @Test
    public void testPrecedence_MultiplicationBeforeAddition() {
        assertEquals(calculator.calculate("1+2*3"), 7.0);
    }

    @Test
    public void testAssociativity_LeftToRightSubtraction() {
        assertEquals(calculator.calculate("10-5\t-2"), 3.0);
    }

    @Test
    public void testDivision_PriorityOverAddition() {
        assertEquals(calculator.calculate("9/3+1"), 4.0);
    }

    @Test
    public void testParentheses_OverridePrecedence() {
        assertEquals(calculator.calculate(" ( 1  +2   )*3"), 9.0);
    }

    @Test
    public void testMultiDigit_ParsedCorrectly() {
        assertEquals(calculator.calculate("12+\n34"), 46.0);
    }
}