package helpers;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class DataGeneratorsTest {

    @Test
    public void testGetRandomIntCountMultiple() {
        int quantityExpected = 8;
        int[] randIntsActual = DataGenerators.getRandomIntArray(quantityExpected, 1, 16);
        assertEquals(randIntsActual.length, quantityExpected);
    }

    @Test
    public void testGetRandomIntCountOne() {
        int quantityExpected = 1;
        int[] randIntsActual = DataGenerators.getRandomIntArray(quantityExpected, 1, 16);
        assertEquals(randIntsActual.length, quantityExpected);
    }

    @Test
    public void testGetRandomIntCountZero() {
        int quantityExpected = 0;
        int[] randIntsActual = DataGenerators.getRandomIntArray(quantityExpected, 1, 16);
        assertEquals(randIntsActual.length, quantityExpected);
    }

    @Test
    public void testGetRandomIntCountNegative() {
        int quantityExpected = -1;
        int[] randIntsActual = DataGenerators.getRandomIntArray(quantityExpected, 1, 16);
        assertEquals(randIntsActual.length, quantityExpected);
    }
}