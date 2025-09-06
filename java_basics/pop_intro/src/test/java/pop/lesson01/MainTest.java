package pop.lesson01;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MainTest {
    @Test()
    public void basicTests() {
        assertEquals(Main.mainStrict1(), "Привет, [Имя]! Сегодня [число] урок Java.");
        assertEquals(Main.mainStrict2(), 5);
        assertEquals(Main.mainStrict3(), 10);
    }
}