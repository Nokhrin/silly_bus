package pop.lesson07;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static pop.lesson07.Capital.createCapitalizedString;

public class CapitalTest {

    @Test
    public void testCreateCapitalizedString() {
        String input = "skyrocket cable twig appraiser";
        String expected = "Skyrocket Cable Twig Appraiser";
        String actual = createCapitalizedString(input);
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateCapitalizedStringEmpty() {
        String input = "";
        String expected = "";
        String actual = createCapitalizedString(input);
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateCapitalizedStringNull() {
        String input = null;
        assertNull(createCapitalizedString(input));
    }
}