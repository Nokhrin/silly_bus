package com.parser.atomic;

import com.parser.core.ParseResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

/**
 * Тесты для IntParser
 *
 * Запуск: mvn test
 * Отчет: target/surefire-reports/emailable-report.html
 */
public class IntParserTest {

    private IntParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new IntParser();
    }

    @Test(description = "Успешный парсинг положительного числа: '123abc' -> 123, offset=3")
    public void parse_positiveInteger() {
        Optional<ParseResult<Integer>> result = parser.parse("123abc", 0);

        assertTrue(result.isPresent(), "Ожидается успешный результат");
        assertEquals(result.get().value(), Integer.valueOf(123), "Значение должно быть 123");
        assertEquals(result.get().end_offset(), 3, "Offset должен указывать на первый нецифровой символ");
    }

    @Test(description = "Успешный парсинг отрицательного числа: '-42xyz' -> -42, offset=3")
    public void parse_negativeInteger() {
        Optional<ParseResult<Integer>> result = parser.parse("-42xyz", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(-42));
        assertEquals(result.get().end_offset(), 3);
    }

    @Test(description = "Успешный парсинг с явным плюсом: '+7test' -> 7, offset=2")
    public void parse_explicitPlus() {
        Optional<ParseResult<Integer>> result = parser.parse("+7test", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(7));
        assertEquals(result.get().end_offset(), 2);
    }

    @Test(description = "Парсинг нуля: '0end' -> 0, offset=1")
    public void parse_zero() {
        Optional<ParseResult<Integer>> result = parser.parse("0end", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(0));
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "Парсинг с начальным offset > 0: '  99' с offset=2 -> 99, offset=4")
    public void parse_withOffset() {
        Optional<ParseResult<Integer>> result = parser.parse("  99", 2);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(99));
        assertEquals(result.get().end_offset(), 4);
    }

    @Test(description = "Неудача: пустая строка -> Optional.empty()")
    public void parse_emptyString() {
        Optional<ParseResult<Integer>> result = parser.parse("", 0);
        assertFalse(result.isPresent(), "Пустая строка должна вернуть failure");
    }

    @Test(description = "Неудача: offset за границей строки -> Optional.empty()")
    public void parse_offsetOutOfBounds() {
        Optional<ParseResult<Integer>> result = parser.parse("123", 5);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: знак без последующих цифр '- abc' -> Optional.empty()")
    public void parse_signWithoutDigits() {
        Optional<ParseResult<Integer>> result = parser.parse("- abc", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: нецифровой символ в начале 'abc123' -> Optional.empty()")
    public void parse_nonDigitStart() {
        Optional<ParseResult<Integer>> result = parser.parse("abc123", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Неудача: переполнение положительного int '2147483648' -> Optional.empty()")
    public void parse_overflow() {
        Optional<ParseResult<Integer>> result = parser.parse("2147483648", 0);
        assertFalse(result.isPresent(), "Число > Integer.MAX_VALUE должно вернуть failure");
    }

    @Test(description = "Неудача: переполнение отрицательного int '-2147483649' -> Optional.empty()")
    public void parse_negativeOverflow() {
        Optional<ParseResult<Integer>> result = parser.parse("-2147483649", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "Парсинг останавливается на первом нецифровом символе: '42+10' -> 42, offset=2")
    public void parse_stopsAtNonDigit() {
        Optional<ParseResult<Integer>> result = parser.parse("42+10", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(42));
        assertEquals(result.get().end_offset(), 2, "Должен остановиться на символе '+'");
    }

    @Test(description = "Неудача: null строка -> Optional.empty()")
    public void parse_nullString() {
        Optional<ParseResult<Integer>> result = parser.parse(null, 0);
        assertFalse(result.isPresent(), "null строка должна вернуть failure");
    }

    @Test(description = "Неудача: отрицательный offset -> Optional.empty()")
    public void parse_negativeOffset() {
        Optional<ParseResult<Integer>> result = parser.parse("123", -1);
        assertFalse(result.isPresent(), "Отрицательный offset должен вернуть failure");
    }

    @Test(description = "Парсинг максимального int: '2147483647' -> Integer.MAX_VALUE, offset=10")
    public void parse_maxInt() {
        Optional<ParseResult<Integer>> result = parser.parse("2147483647", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(Integer.MAX_VALUE));
        assertEquals(result.get().end_offset(), 10);
    }

    @Test(description = "Парсинг минимального int: '-2147483648' -> Integer.MIN_VALUE, offset=11")
    public void parse_minInt() {
        Optional<ParseResult<Integer>> result = parser.parse("-2147483648", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(Integer.MIN_VALUE));
        assertEquals(result.get().end_offset(), 11);
    }
}