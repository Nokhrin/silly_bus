package pop.lesson03;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Task1Test {

    @Test
    public void testGetMax() {
        Task1 lesson03 = new Task1();
        assertEquals(lesson03.getMax(lesson03.target), 5);
    }

    @Test
    public void testGetAvg() {
        Task1 lesson03 = new Task1();
        assertEquals(lesson03.getAvg(lesson03.target), "2.80");
    }
}