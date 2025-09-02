package pop.lesson02;

import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.*;

public class MainTest {

    @Test
    public void testSum() {
        Main lesson02 = new Main();
        assertEquals(lesson02.sum(-1, 1), 0);
        assertEquals(lesson02.sum(0, 1), 1);
        assertEquals(lesson02.sum(0, -1), -1);
        assertEquals(lesson02.sum(-2, 1), -1);
        assertEquals(lesson02.sum(-1, 2), 1);
    }

    @Test
    public void testIsEven() {
        Main lesson02 = new Main();
        assertTrue(lesson02.isEven(0));
        assertTrue(lesson02.isEven(2));
        assertFalse(lesson02.isEven(1));
        assertFalse(lesson02.isEven(-1));
    }

    @Test
    public void testFactorialIterative() {
        Main lesson02 = new Main();

        assertEquals(lesson02.factorial(1), BigInteger.valueOf(1));
        assertEquals(lesson02.factorial(2), BigInteger.valueOf(2));
        assertEquals(lesson02.factorial(5), BigInteger.valueOf(120));
        assertEquals(lesson02.factorial(16), BigInteger.valueOf(20_922_789_888_000L));
        assertEquals(lesson02.factorial(20), BigInteger.valueOf(2_432_902_008_176_640_000L));
        assertEquals(lesson02.factorial(42), new BigInteger("1405006117752879898543142606244511569936384000000000"));

        assertEquals(lesson02.factorialIterativeForLoopLong(1), 1L);
        assertEquals(lesson02.factorialIterativeForLoopLong(2), 2L);
        assertEquals(lesson02.factorialIterativeForLoopLong(5), 120L);
        assertEquals(lesson02.factorialIterativeForLoopLong(16), 20_922_789_888_000L);
        assertEquals(lesson02.factorialIterativeForLoopLong(20), 2_432_902_008_176_640_000L);

        assertEquals(lesson02.factorialIterativeForLoopInt(1), 1L);
        assertEquals(lesson02.factorialIterativeForLoopInt(2), 2L);
        assertEquals(lesson02.factorialIterativeForLoopInt(5), 120L);

    }

    @Test
    public void testFactorialRecursive() {
        Main lesson02 = new Main();

        assertEquals(lesson02.factorialRecursiveLong(1), 1L);
        assertEquals(lesson02.factorialRecursiveLong(2), 2L);
        assertEquals(lesson02.factorialRecursiveLong(5), 120L);
        assertEquals(lesson02.factorialRecursiveLong(16), 20_922_789_888_000L);
    }
}