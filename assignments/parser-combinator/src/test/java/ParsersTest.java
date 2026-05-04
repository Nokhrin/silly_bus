import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class ParsersTest {
//символ совпал, символ не совпал, offset за пределами строки

    @Test(description = "символ совпал")
    public void testCharMatch() {

        Parser<Character> parser = Parsers.characterParser('a');
        Optional<Parsed<Character>> result = parser.apply("a", 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value().equals('a'));
        assertTrue(result.get().offset().equals(1));
    }
}