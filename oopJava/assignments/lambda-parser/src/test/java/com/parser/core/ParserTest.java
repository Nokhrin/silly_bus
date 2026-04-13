package com.parser.core;

import com.parser.atomic.BinaryOperatorParser;
import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class ParserTest {
    /**
     * Парсер Integer
     */
    IntParser integerParser = new IntParser();

    /**
     * Парсер пробельных символов
     */
    WhitespaceParser whitespaceParser = new WhitespaceParser();

    /**
     * Мок-парсер строки "123"
     */
    private final Parser<String> stringParser = (source, offset) -> {
        if (source.startsWith("123", offset)) {
            return Optional.of(ParseResult.of("123", offset + 3));
        }
        return Optional.empty();
    };

    /**
     * Мок-парсер char 'a'
     */
    private final Parser<Character> characterParser = (source, offset) -> {
        if (offset < source.length() && source.charAt(offset) == 'a') {
            return Optional.of(ParseResult.of('a', offset + 1));
        }
        ;
        return Optional.empty();
    };

    @Test(description = "Map: Преобразование String в Integer")
    public void testMap_stringToInteger() {
        Parser<Integer> integerParser = stringParser.map(Integer::parseInt);

        Optional<ParseResult<Integer>> result = integerParser.parse("123abc", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(123));
        assertEquals(result.get().end_offset(), 3);
    }

    @Test(description = "Map: Цепочка преобразований")
    public void testMap_chaining() {
        Parser<String> upperParser = stringParser.map(String::toUpperCase);
        Parser<Integer> lengthParser = upperParser.map(String::length);

        Optional<ParseResult<Integer>> result = lengthParser.parse("123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(3));
    }

    @Test(description = "flatMap: Успешная фильтрация четного числа")
    public void testflatMap_success() {
        Parser<Integer> integerParser = stringParser.map(Integer::parseInt);

        Parser<Integer> evenParser = integerParser.flatMap(val ->
                (val % 2 == 0) ? Optional.of(val) : Optional.empty()
        );

        Optional<ParseResult<Integer>> result = evenParser.parse("123", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "flatMap: Успешная фильтрация любого значения")
    public void testflatMap_filterOut() {
        Parser<Integer> integerParser = stringParser.map(Integer::parseInt);

        Parser<Integer> rejectParser = integerParser.flatMap(val -> Optional.empty());

        Optional<ParseResult<Integer>> result = rejectParser.parse("123", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "flatMap: Если исходный парсер фейлится, flatMap не вызывается")
    public void testflatMap_shortCircuit() {
        Parser<String> failParser = (s, o) -> Optional.empty();

        Parser<String> mapped = failParser.flatMap(val -> {
            throw new RuntimeException("Прерывание парсинга");
        });

        Optional<ParseResult<String>> result = mapped.parse("test", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "BinaryOperatorParser + map: преобразование enum в символ")
    public void testBinaryOperatorParser_map_enumToChar() {
        BinaryOperatorParser opParser = new BinaryOperatorParser();

        Parser<Character> charParser = opParser.map(BinaryOperator::getOperator);

        Optional<ParseResult<Character>> result = charParser.parse("+123", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Character.valueOf('+'));
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "BinaryOperatorParser + flatMap: разрешены `+` и `-`)")
    public void testBinaryOperatorParser_flatMap_filterArithmetic() {
        BinaryOperatorParser opParser = new BinaryOperatorParser();

        Parser<BinaryOperator> arithmeticParser = opParser.flatMap(op ->
                (op == BinaryOperator.ADD || op == BinaryOperator.SUB)
                        ? Optional.of(op)
                        : Optional.empty()
        );

        Optional<ParseResult<BinaryOperator>> addResult = arithmeticParser.parse("+5", 0);
        assertTrue(addResult.isPresent());
        assertEquals(addResult.get().value(), BinaryOperator.ADD);

        Optional<ParseResult<BinaryOperator>> mulResult = arithmeticParser.parse("*5", 0);
        assertFalse(mulResult.isPresent());
    }

    //region plus
    @Test(description = "Plus: Успешная последовательность (Int + Char)")
    public void testPlus_success_sequence() {
        Parser<Tuple2<Integer, Character>> sequence = integerParser.plus(characterParser);

        Optional<ParseResult<Tuple2<Integer, Character>>> result = sequence.parse("123a_rest", 0);

        assertTrue(result.isPresent());

        assertEquals(result.get().value().a(), Integer.valueOf(123));
        assertEquals(result.get().value().b(), 'a');

        assertEquals(result.get().end_offset(), 4);
    }

    @Test(description = "Plus: неудача на первом парсере")
    public void testPlus_fail_first() {
        Parser<Tuple2<Integer, Character>> sequence = integerParser.plus(characterParser);
        Optional<ParseResult<Tuple2<Integer, Character>>> result = sequence.parse("abc", 0);

        assertFalse(result.isPresent());
    }

    @Test(description = "Plus: неудача на втором парсере")
    public void testPlus_fail_second() {
        Parser<Tuple2<Integer, Character>> sequence = integerParser.plus(characterParser);
        Optional<ParseResult<Tuple2<Integer, Character>>> result = sequence.parse("123b", 0);

        assertFalse(result.isPresent());
    }

    @Test(description = "Plus: Цепочка из трех элементов (A + B + C)")
    public void testPlus_chain() {
        Parser<Tuple2<Integer, Character>> part1 = integerParser.plus(characterParser);
        Parser<String> charBParser = (s, o) -> (o < s.length() && s.charAt(o) == 'b')
                ? Optional.of(ParseResult.of("b", o + 1))
                : Optional.empty();

        Parser<Tuple2<Tuple2<Integer, Character>, String>> fullSequence = part1.plus(charBParser);

        Optional<ParseResult<Tuple2<Tuple2<Integer, Character>, String>>> result =
                fullSequence.parse("123ab", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().a().a(), Integer.valueOf(123));
        assertEquals(result.get().value().a().b(), 'a');

        assertEquals(result.get().value().b(), "b");

        assertEquals(result.get().end_offset(), 5);
    }
    //endregion plus

    //region repeat, optional
    @Test(description = "Repeat: Успешный парсинг нескольких чисел (min=2, max=5)")
    public void testRepeat_multiple_success() {
        Parser<Integer> intWsParser = integerParser
                .plus(whitespaceParser.optional())
                .map(Tuple2::a);
        Parser<List<Integer>> listParser = intWsParser.repeat(2, 5);
        Optional<ParseResult<List<Integer>>> result = listParser.parse("10 20 30", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value().size(), 3);
        assertEquals(result.get().value().get(0), Integer.valueOf(10));
        assertEquals(result.get().value().get(1), Integer.valueOf(20));
        assertEquals(result.get().value().get(2), Integer.valueOf(30));
    }

    @Test(description = "Optional: Элемент найден")
    public void testOptional_present() {
        Parser<Optional<Integer>> optionalParser = integerParser.optional();
        Optional<ParseResult<Optional<Integer>>> result = optionalParser.parse("42 text", 0);

        assertTrue(result.isPresent());
        assertTrue(result.get().value().isPresent());
        assertEquals(result.get().value().get(), Integer.valueOf(42));
    }

    //endregion repeat, optional
}