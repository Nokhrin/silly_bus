package fundamentals;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CalculatorTest {
    @Test
    void simpleCalcTests() {
        assertEquals(Calculator.findAverage(new int[] {100, 50, 25, 75, 0}), 50.0);
        assertEquals(Calculator.findAverage(new int[] {5, 5, 5, 5}), 5.0);
        assertEquals(Calculator.findAverage(new int[] {1, 2, 3}), 2.0);
        assertEquals(Calculator.findAverage(new int[] {}), 0.0);
        assertEquals(Calculator.findAverage(new int[] {6, 4, 5, 2, 7}), 4.8);
    }

    @Test
    void enhancedCalcTests() {
        assertEquals(Calculator.findAverageEnhanced(new int[] {100, 50, 25, 75, 0}), 50.0);
        assertEquals(Calculator.findAverageEnhanced(new int[] {5, 5, 5, 5}), 5.0);
        assertEquals(Calculator.findAverageEnhanced(new int[] {1, 2, 3}), 2.0);
        assertEquals(Calculator.findAverageEnhanced(new int[] {}), 0.0);
        assertEquals(Calculator.findAverageEnhanced(new int[] {6, 4, 5, 2, 7}), 4.8);
    }
}