package pop.lesson02;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MainTest {

    @Test
    public void testSum() {
        Main lesson02 = new Main();
        assertEquals(lesson02.sum(-1, 1), 0);
        assertEquals(lesson02.sum(0, 1), 1);
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
    public void testFactorial_iterative() {
        Main lesson02 = new Main();
        assertEquals(lesson02.factorial_iterative(1), 1L);
        assertEquals(lesson02.factorial_iterative(2), 2L);
        assertEquals(lesson02.factorial_iterative(5), 120L);
        assertEquals(lesson02.factorial_iterative(16), 20_922_789_888_000L);
    }

    @Test
    public void testFactorial_recursive() {
        Main lesson02 = new Main();
        assertEquals(lesson02.factorial_iterative(1), 1L);
        assertEquals(lesson02.factorial_iterative(2), 2L);
        assertEquals(lesson02.factorial_iterative(5), 120L);
        assertEquals(lesson02.factorial_iterative(16), 20_922_789_888_000L);
    }
}