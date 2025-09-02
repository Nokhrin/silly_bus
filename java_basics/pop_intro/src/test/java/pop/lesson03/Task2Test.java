package pop.lesson03;

import org.testng.annotations.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.*;

public class Task2Test {

    @Test
    public void testMapNameToAgeFixed() {
        Map<String, Integer> nameToAge = Stream.of(new Object[][] {
                {"Cóem", 1},
                {"Romey", 2},
                {"Sadhbh", 3},
                {"Kyro", 4},
                {"Paula", 5},
        }).collect(Collectors.toMap(nameAge -> (String) nameAge[0], nameAge -> (Integer) nameAge[1]));
        assertEquals(Task2.mapNameToAgeFixed(), nameToAge);
    }
}
