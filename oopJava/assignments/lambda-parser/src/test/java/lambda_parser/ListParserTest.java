package lambda_parser;

import org.testng.annotations.Test;
import java.util.List;
import java.util.Optional;
import static org.testng.Assert.*;

public class ListParserTest {
    // Stub-парсер: матчит только 'a', потребляет ровно 1 символ.
    // Изолирует тесты от особенностей IntParser (пробелы, длинные числа).
    private final Parser<Character> charA = new Parser<>() {
        @Override
        public Optional<ParseResult<Character>> parse(String source, int offset) {
            if (offset < source.length() && source.charAt(offset) == 'a') {
                return Optional.of(new ParseResultImpl<>('a', offset + 1));
            }
            return Optional.empty();
        }
    };

    @Test(description = "Жадный парсинг до max (min=1, max=3)")
    void testGreedyUpToMax() throws IllegalAccessException {
        Parser<List<Character>> parser = new ListParser<>(charA, 1, 3);
        var res = parser.parse("aaaa", 0);

        assertTrue(res.isPresent());
        assertEquals(List.of('a', 'a', 'a'), res.get().value());
        assertEquals(3, res.get().end_offset());
    }

    @Test(description = "Ошибка: совпадений меньше min (min=2, max=5)")
    void testFailLessThanMin() throws IllegalAccessException {
        Parser<List<Character>> parser = new ListParser<>(charA, 2, 5);
        var res = parser.parse("ab", 0); // только 1 совпадение 'a'

        assertTrue(res.isEmpty());
    }

    @Test(description = "min=0 разрешает пустой результат (эмуляция [])")
    void testZeroMinAllowsEmpty() throws IllegalAccessException {
        Parser<List<Character>> parser = new ListParser<>(charA, 0, 2);
        var res = parser.parse("bca", 0); // 'a' встречается только в конце

        assertTrue(res.isPresent());
        assertTrue(res.get().value().isEmpty());
        assertEquals(0, res.get().end_offset());
    }
}