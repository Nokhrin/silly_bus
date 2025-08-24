package pop.lesson01;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MainTest {
    @Test()
    public void basicTests() {
        Main lesson = new Main();
        assertEquals(1, 0);
    }
}