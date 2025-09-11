package pop.lesson03;

import org.testng.annotations.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.testng.Assert.assertEquals;
import static pop.lesson03.CollectionsBasics.createSortedMap;

public class CollectionsBasicsTest {

    @Test
    public void testCreateSortedMap() {
        SortedMap<String, Integer> expected = new TreeMap<>();
        expected.put("Paula", 5);

        SortedMap<String, Integer> actual = createSortedMap("Paula", 5);

        assertEquals(actual, expected);
    }
}