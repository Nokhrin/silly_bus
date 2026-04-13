package com.parser.atomic;

import com.parser.core.ParseResult;
import com.parser.core.Parser;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Парсер целых чисел по основанию 10.
 *
 * Поддерживает integer = последовательность цифр, возможно со знаком. Останавливается на первом не-цифровом символе
 * - положительные числа: "123" -> 123
 * - отрицательные числа: "-42" -> -42
 * - ноль: "0" -> 0
 *
 * Time: O(k), k - количество цифр
 * Space: O(1)
 */
public class IntParser implements Parser<Integer> {

    private static final Logger logger = Logger.getLogger(IntParser.class.getName());

    /**
     * Лямбда верификации цифры
     */
    private final Predicate<Character> isDigit = c -> c >= '0' && c <= '9';

    /**
     * Парсит целое число
     * [+-]?[0-9]+
     *
     * @param source входная строка
     * @param begin_offset позиция начала парсинга
     * @return результат парсинга или Optional.empty() при неудаче
     */
    @Override
    public Optional<ParseResult<Integer>> parse(String source, int begin_offset) {
        if (source == null || begin_offset < 0 || begin_offset >= source.length()) {
            logger.log(Level.FINE, "Ошибка ввода: source={0}, offset={1}",
                    new Object[]{source, begin_offset});
            return Optional.empty();
        }

        int offset = begin_offset;
        int sign = 1;

        // Знак
        if (source.charAt(offset) == '-') {
            sign = -1;
            offset++;
            logger.log(Level.FINEST, "Считан знак `-` в смещении {0}", begin_offset);
        } else if (source.charAt(offset) == '+') {
            offset++;
            logger.log(Level.FINEST, "Считан знак `+` в смещении {0}", begin_offset);
        }

        // наличие цифр после знака
        if (offset >= source.length() || !isDigit.test(source.charAt(offset))
        ) {
            logger.log(Level.FINE, "Нет цифр после знака в смещении {0}", begin_offset);
            return Optional.empty();
        }

        // полная подстрока цифр
        int start = offset;
        while (offset < source.length() && isDigit.test(source.charAt(offset))) {
            offset++;
            logger.log(Level.FINEST, "Считана подстрока в диапазоне [{0};{1}]", new Object[]{start, offset - 1});
        }

        // результат
        String digits = source.substring(start, offset);

        // long для валидации - корректное значение -2147483648 вызовет переполнение при Integer.parseInt()
        long longValue = sign * Long.parseLong(digits);

        if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
            logger.log(Level.FINE, "Переполнение Integer в смещении {0}",
                    new Object[]{begin_offset});
            return Optional.empty();
        }

        int value = (int) longValue;
        logger.log(Level.FINE,
                "Считано значение в диапазоне [{0};{1}]",
                new Object[]{start, offset - 1});
        return Optional.of(new ParseResult<>(value, offset));
    }
}
