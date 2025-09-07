package pop.lesson01;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HelloMainTest {
    @Test()
    public void basicTests() {
        assertEquals(HelloMain.mainStrict1(), "Привет, [Имя]! Сегодня [число] урок Java.");
        assertEquals(HelloMain.mainStrict2(), 5);
        assertEquals(HelloMain.mainStrict3(), 10);
    }
}