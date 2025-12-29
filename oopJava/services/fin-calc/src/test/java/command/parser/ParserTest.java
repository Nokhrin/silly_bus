package command.parser;

import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

@Test
public class ParserTest {

    //region Constants

    /**
     * Набор пробельных символов: пробел, табуляция, возврат каретки, новая строка.
     */
    private static final Set<Character> WHITESPACES = Set.of(' ', '\t', '\n', '\r');

    /**
     * Регулярное выражение для валидации формата UUID.
     */
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[a-f\\d]{8}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{12}$", Pattern.CASE_INSENSITIVE
    );

    //endregion

    //region parseWhitespace

    /**
     * Тестирование парсинга пробельных символов.
     */
    @Test
    public void testParseWhitespace() {
        // Позитивные случаи
        Optional<ParseResult<String>> result1 = Parser.parseWhitespace("   ", 0);
        assertTrue(result1.isPresent());
        assertEquals(result1.get().value(), "");
        assertEquals(result1.get().start(), 0);
        assertEquals(result1.get().end(), 3);

        Optional<ParseResult<String>> result2 = Parser.parseWhitespace("\t\n\r", 0);
        assertTrue(result2.isPresent());
        assertEquals(result2.get().value(), "");
        assertEquals(result2.get().start(), 0);
        assertEquals(result2.get().end(), 3);

        Optional<ParseResult<String>> result3 = Parser.parseWhitespace(" \t\n", 0);
        assertTrue(result3.isPresent());
        assertEquals(result3.get().value(), "");
        assertEquals(result3.get().start(), 0);
        assertEquals(result3.get().end(), 3);

        // Негативные случаи
        Optional<ParseResult<String>> result4 = Parser.parseWhitespace("", 0);
        assertFalse(result4.isPresent());

        Optional<ParseResult<String>> result5 = Parser.parseWhitespace("abc", 0);
        assertFalse(result5.isPresent());

        Optional<ParseResult<String>> result6 = Parser.parseWhitespace("a\tb", 0);
        assertFalse(result6.isPresent());

        Optional<ParseResult<String>> result7 = Parser.parseWhitespace(" \t", 0);
        assertTrue(result7.isPresent());
        assertEquals(result7.get().end(), 2);

        // Проверка пустого результата
        Optional<ParseResult<String>> result8 = Parser.parseWhitespace("notWhitespace", 0);
        assertFalse(result8.isPresent());
    }

    //endregion

    //region parseAccountId

    /**
     * Тестирование парсинга идентификатора счёта в формате UUID.
     */
    @Test
    public void testParseAccountId() {
        // Валидные UUID
        Optional<ParseResult<String>> result1 = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8", 0);
        assertTrue(result1.isPresent());
        assertEquals(result1.get().value(), "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8");
        assertEquals(result1.get().start(), 0);
        assertEquals(result1.get().end(), 36);

        Optional<ParseResult<String>> result2 = Parser.parseAccountId("f47ac10b-58a1-4344-b672-e6a653f3e4da", 0);
        assertTrue(result2.isPresent());
        assertEquals(result2.get().value(), "f47ac10b-58a1-4344-b672-e6a653f3e4da");
        assertEquals(result2.get().start(), 0);
        assertEquals(result2.get().end(), 36);

        // Некорректные UUID
        Optional<ParseResult<String>> result3 = Parser.parseAccountId("123", 0);
        assertFalse(result3.isPresent());

        Optional<ParseResult<String>> result4 = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n", 0);
        assertFalse(result4.isPresent());

        Optional<ParseResult<String>> result5 = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8z", 0);
        assertFalse(result5.isPresent());

        Optional<ParseResult<String>> result6 = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8", 1);
        assertFalse(result6.isPresent());

        // Проверка, что метод не принимает идентификаторы с символами, не относящимися к шестнадцатеричной системе
        Optional<ParseResult<String>> result7 = Parser.parseAccountId("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8!", 0);
        assertFalse(result7.isPresent());

        // Проверка, что метод не срабатывает на пустой строке
        Optional<ParseResult<String>> result8 = Parser.parseAccountId("", 0);
        assertFalse(result8.isPresent());
    }

    //endregion

    //region UUID_PATTERN

    /**
     * Тестирование регулярного выражения UUID_PATTERN.
     */
    @Test
    public void testUUID_PATTERN() {
        assertTrue(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8").matches());
        assertTrue(UUID_PATTERN.matcher("f47ac10b-58a1-4344-b672-e6a653f3e4da").matches());
        assertTrue(UUID_PATTERN.matcher("A1B2C3D4-E5F6-7890-G1H2-I3J4K5L6M7N8").matches());
        assertTrue(UUID_PATTERN.matcher("12345678-1234-1234-1234-123456789abc").matches());

        assertFalse(UUID_PATTERN.matcher("123").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8z").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8!").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8 ").matches());
        assertFalse(UUID_PATTERN.matcher("").matches());
        assertFalse(UUID_PATTERN.matcher("a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8-").matches());
    }

    //endregion
}