package com.parser.combinator;

import com.parser.core.ParseResult;
import com.parser.core.Parser;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ListParserTest {

    /**
     * Мок-парсер, парсит char 'a'
     */
    private final Parser<Character> mockParser = (source, offset) -> {
        if (offset < source.length() && source.charAt(offset) == 'a') {
            return Optional.of(new ParseResult<>('a', offset + 1));
        }
        return Optional.empty();
    };

    @Test(description = "Успешный парсинг: > min и < max")
    public void testParse_greedy_success() {
        Parser<List<Character>> parser = new ListParser<>(mockParser, 1, 3);
        Optional<ParseResult<List<Character>>> result = parser.parse("aab", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().size(), 2);
        assertEquals(result.get().end_offset(), 2);
    }

    @Test(description = "Успешный парсинг: совпадения не требуются и не найдены")
    public void testParse_optional_success() {
        Parser<List<Character>> parser = new ListParser<>(mockParser, 0, 5);
        Optional<ParseResult<List<Character>>> result = parser.parse("b", 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value().isEmpty());
        assertEquals(result.get().end_offset(), 0);
    }

    @Test(description = "Неудача: совпадений < min")
    public void testParse_failure_min_not_met() {
        Parser<List<Character>> parser = new ListParser<>(mockParser, 2, 5);
        Optional<ParseResult<List<Character>>> result = parser.parse("ab", 0);

        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: вход пустой, min=1")
    public void testParse_empty_input_failure() {
        Parser<List<Character>> parser = new ListParser<>(mockParser, 1, 1);
        Optional<ParseResult<List<Character>>> result = parser.parse("", 0);

        assertFalse(result.isPresent());
    }

    @Test(description = "Зацикливание: Успешный парсинг без смещения")
    public void testParse_infinite_loop_protection() {
        Parser<String> epsilonParser = (source, offset) -> Optional.of(new ParseResult<>("", offset));
        Parser<List<String>> parser = new ListParser<>(epsilonParser, 0, 100);
        assertThrows(IllegalStateException.class, () -> parser.parse("test", 0));
    }
}