package com.parser.core;

import com.parser.atomic.BinaryOperatorParser;
import com.parser.atomic.IntParser;
import com.parser.atomic.WhitespaceParser;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class ParserTest {
    private final Parser<String> stringParser = (source, offset) -> {
        if (source.startsWith("123", offset)) {
            return Optional.of(ParseResult.of("123", offset + 3));
        }
        return Optional.empty();
    };

    @Test(description = "Map: Преобразование String в Integer")
    public void testMap_stringToInteger() {
        // Создаем парсер Integer через map
        Parser<Integer> intParser = stringParser.map(Integer::parseInt);

        Optional<ParseResult<Integer>> result = intParser.parse("123abc", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(123));
        assertEquals(result.get().end_offset(), 3);
    }

    @Test(description = "Map: Цепочка преобразований")
    public void testMap_chaining() {
        Parser<String> upperParser = stringParser.map(String::toUpperCase);
        Parser<Integer> lengthParser = upperParser.map(String::length); // "123" -> "123" -> 3

        Optional<ParseResult<Integer>> result = lengthParser.parse("123", 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(3));
    }

    @Test(description = "flatMap: Успешная фильтрация (значение четное)")
    public void testflatMap_success() {
        Parser<Integer> intParser = stringParser.map(Integer::parseInt);

        // Оставляем только четные числа
        Parser<Integer> evenParser = intParser.flatMap(val ->
                (val % 2 == 0) ? Optional.of(val) : Optional.empty()
        );

        // 123 нечетное, должно фейлиться
        Optional<ParseResult<Integer>> result = evenParser.parse("123", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "flatMap: Пропуск значения (возврат Optional.empty)")
    public void testflatMap_filterOut() {
        Parser<Integer> intParser = stringParser.map(Integer::parseInt);

        // Всегда возвращаем empty -> парсер всегда фейлится
        Parser<Integer> rejectParser = intParser.flatMap(val -> Optional.empty());

        Optional<ParseResult<Integer>> result = rejectParser.parse("123", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "flatMap: Если исходный парсер фейлится, flatMap не вызывается")
    public void testflatMap_shortCircuit() {
        Parser<String> failParser = (s, o) -> Optional.empty();

        // Функция не должна выполниться
        Parser<String> mapped = failParser.flatMap(val -> {
            throw new RuntimeException("Не должно выполниться");
        });

        Optional<ParseResult<String>> result = mapped.parse("test", 0);
        assertFalse(result.isPresent());
    }

    @Test(description = "IntParser + map: String-парсинг не нужен, проверяем flatMap-фильтрацию")
    public void testIntParser_flatMap_filterPositive() {
        IntParser intParser = new IntParser();

        // Оставляем только положительные числа
        Parser<Integer> positiveParser = intParser.flatMap(val ->
                val > 0 ? Optional.of(val) : Optional.empty()
        );

        // Положительное: успех
        Optional<ParseResult<Integer>> posResult = positiveParser.parse("42", 0);
        assertTrue(posResult.isPresent());
        assertEquals(posResult.get().value(), Integer.valueOf(42));

        // Отрицательное: отфильтровано
        Optional<ParseResult<Integer>> negResult = positiveParser.parse("-5", 0);
        assertFalse(negResult.isPresent());
    }

    @Test(description = "WhitespaceParser + map: трансформация маркера в длину пробелов")
    public void testWhitespaceParser_map_transformToCount() {
        WhitespaceParser wsParser = new WhitespaceParser();

        // Трансформируем Whitespace в количество потреблённых символов
        // Для этого нужен доступ к исходному offset, поэтому используем кастомный map через Parser
        Parser<Integer> wsLengthParser = (source, offset) -> {
            Optional<ParseResult<Whitespace>> wsResult = wsParser.parse(source, offset);
            if (wsResult.isPresent()) {
                int consumed = wsResult.get().end_offset() - offset;
                return Optional.of(ParseResult.of(consumed, wsResult.get().end_offset()));
            }
            return Optional.empty();
        };

        Optional<ParseResult<Integer>> result = wsLengthParser.parse("   text", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Integer.valueOf(3)); // 3 пробела
    }

    @Test(description = "BinaryOperatorParser + map: преобразование enum в символ")
    public void testBinaryOperatorParser_map_enumToChar() {
        BinaryOperatorParser opParser = new BinaryOperatorParser();

        // BinaryOperator -> Character (символ оператора)
        Parser<Character> charParser = opParser.map(BinaryOperator::getOperator);

        Optional<ParseResult<Character>> result = charParser.parse("+123", 0);
        assertTrue(result.isPresent());
        assertEquals(result.get().value(), Character.valueOf('+'));
        assertEquals(result.get().end_offset(), 1);
    }

    @Test(description = "BinaryOperatorParser + flatMap: разрешаем только арифметику (+, -)")
    public void testBinaryOperatorParser_flatMap_filterArithmetic() {
        BinaryOperatorParser opParser = new BinaryOperatorParser();

        // Разрешаем только ADD и SUB
        Parser<BinaryOperator> arithmeticParser = opParser.flatMap(op ->
                (op == BinaryOperator.ADD || op == BinaryOperator.SUB)
                        ? Optional.of(op)
                        : Optional.empty()
        );

        // '+' разрешён
        Optional<ParseResult<BinaryOperator>> addResult = arithmeticParser.parse("+5", 0);
        assertTrue(addResult.isPresent());
        assertEquals(addResult.get().value(), BinaryOperator.ADD);

        // '*' запрещён
        Optional<ParseResult<BinaryOperator>> mulResult = arithmeticParser.parse("*5", 0);
        assertFalse(mulResult.isPresent());
    }
}