package pop.lesson01;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static pop.lesson01.StringBasics.convertToBinary;
import static pop.lesson01.StringBasics.getGreeting;

public class StringBasicsTest {

    @Test
    public void testGetGreeting() {
        String expected = "Привет, мир! Сегодня 2147483647 урок Java.";
        String actual = getGreeting("мир", Integer.MAX_VALUE);
        assertEquals(actual, expected);
    }

    @Test
    public void testConvertToBinary() {
        assertEquals("01011110001100111", convertToBinary("45385593107843568"));
        assertEquals("101000111101101", convertToBinary("509321967506747"));
        assertEquals("011011110000101010000011011", convertToBinary("366058562030849490134388085"));
    }
}