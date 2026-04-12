package com.parser.atomic;

import com.parser.core.BinaryOperator;
import com.parser.core.ParseResult;
import com.parser.core.Parser;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Парсер мат операций.
 *
 * Time: O(1)
 * Space: O(1)
 */
public class BinaryOperatorParser implements Parser<BinaryOperator> {

    private static final Logger logger = Logger.getLogger(BinaryOperatorParser.class.getName());

    /**
     * Парсит оператор
     * Обработка унарных +/- происходит в парсере Integer
     * Данный парсер ожидает один оператор между двумя Integer
     *
     * @param source входная строка
     * @param begin_offset позиция начала парсинга
     * @return результат парсинга или Optional.empty() при неудаче
     */
    @Override
    public Optional<ParseResult<BinaryOperator>> parse(String source, int begin_offset) {
        if (source == null || begin_offset < 0 || begin_offset >= source.length()) {
            logger.log(Level.FINE, "Ошибка ввода: source={0}, offset={1}",
                    new Object[]{source, begin_offset});
            return Optional.empty();
        }

        char charAt = source.charAt(begin_offset);
        logger.log(Level.FINE, "Считан символ по смещению {0}", begin_offset);
        BinaryOperator operator = BinaryOperator.valueOf(charAt);

        if (operator != null) {
            logger.log(Level.FINE, "Считан бинарный оператор {0} по смещению {1}",
                    new Object[]{operator, begin_offset});
            return Optional.of(ParseResult.of(operator, begin_offset + 1));
        }

        logger.log(Level.FINE, "Бинарный оператор не найден");
        return Optional.empty();
    }
}
