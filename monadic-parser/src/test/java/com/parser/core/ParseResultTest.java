package com.parser.core;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 *  корректность хранения данных и работы map() в ParseResult
 */
public class ParseResultTest {
    @Test(description = "Успешное создание экземпляра")
    public void testOf_createsInstance() {
        String expectedValue = "test_data";
        int expectedOffset = 42;

        ParseResult<String> result = new ParseResult<>(expectedValue, expectedOffset);

        assertEquals(result.value(), expectedValue);
        assertEquals(result.end_offset(), expectedOffset);
    }

    @Test(description = "Корректные трансформация значения, сохранение смещения")
    public void testMap_transformsValue() {
        ParseResult<String> original = new ParseResult<>("123", 10);

        ParseResult<Integer> mapped = original.map(Integer::parseInt);

        assertEquals(mapped.value(), Integer.valueOf(123));
        assertEquals(mapped.end_offset(), 10);
    }

    @Test(description = "Корректная цепочка вызовов map")
    public void testMap_chaining() {
        ParseResult<Integer> result = new ParseResult<>(10, 0)
                .map(val -> val * 2)      // 20
                .map(val -> val + 5);     // 25

        assertEquals(result.value(), Integer.valueOf(25));
        assertEquals(result.end_offset(), 0);
    }
}