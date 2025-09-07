package pop.lesson02;

import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.*;

public class MathMainTest {

    @Test
    public void testSum() {
        assertEquals(MathMain.sum(-1, 1), 0);
        assertEquals(MathMain.sum(0, 1), 1);
        assertEquals(MathMain.sum(0, -1), -1);
        assertEquals(MathMain.sum(-2, 1), -1);
        assertEquals(MathMain.sum(-1, 2), 1);
        assertEquals(MathMain.sum(-1, 2), 1);
        assertEquals(MathMain.sum(Integer.MIN_VALUE, Integer.MAX_VALUE), -1);
    }

    @Test
    public void testIsEven() {
        assertTrue(MathMain.isEven(0));
        assertTrue(MathMain.isEven(10));
        assertTrue(MathMain.isEven(Integer.MIN_VALUE));
        assertFalse(MathMain.isEven(1));
        assertFalse(MathMain.isEven(11));
        assertFalse(MathMain.isEven(-1));
        assertFalse(MathMain.isEven(Integer.MAX_VALUE));
    }

    @Test
    public void testFactorialIterative() {
        assertEquals(MathMain.factorialIterative(0), BigInteger.valueOf(1));
        assertEquals(MathMain.factorialIterative(1), BigInteger.valueOf(1));
        assertEquals(MathMain.factorialIterative(2), BigInteger.valueOf(2));
        assertEquals(MathMain.factorialIterative(5), BigInteger.valueOf(120));
        assertEquals(MathMain.factorialIterative(16), BigInteger.valueOf(20_922_789_888_000L));
        assertEquals(MathMain.factorialIterative(20), BigInteger.valueOf(2_432_902_008_176_640_000L));
        assertEquals(MathMain.factorialIterative(42), new BigInteger("1405006117752879898543142606244511569936384000000000"));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testFactorialIterativeInvalidParams() {
        MathMain.factorialIterative(-1);
    }

    @Test
    public void testFactorialRecursive() {
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(0)), BigInteger.valueOf(1));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(2)), BigInteger.valueOf(2));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(5)), BigInteger.valueOf(120));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(16)), BigInteger.valueOf(20_922_789_888_000L));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(20)), BigInteger.valueOf(2_432_902_008_176_640_000L));
        assertEquals(MathMain.factorialRecursive(BigInteger.valueOf(42)), new BigInteger("1405006117752879898543142606244511569936384000000000"));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testFactorialRecursiveInvalidParams() {
        MathMain.factorialRecursive(BigInteger.valueOf(-1));
    }
}