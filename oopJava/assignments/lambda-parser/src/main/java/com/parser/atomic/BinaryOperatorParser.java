package com.parser.atomic;

import com.parser.core.BinaryOperator;
import com.parser.core.ParseResult;
import com.parser.core.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Парсер мат операций.
 *
 * Time: O(1)
 * Space: O(1)
 */
public class BinaryOperatorParser implements Parser<BinaryOperator> {

    static final Logger log = LoggerFactory.getLogger(BinaryOperatorParser.class);

    /**
     * Возвращает строковое представление парсера
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

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
            log.error("Ошибка ввода: source={}, offset={}", source, begin_offset);
            return Optional.empty();
        }

        char charAt = source.charAt(begin_offset);
        log.debug("Считан символ по смещению {}", begin_offset);
        BinaryOperator operator = BinaryOperator.valueOf(charAt);

        if (operator != null) {
            log.info("Считан бинарный оператор {} по смещению {}", operator, begin_offset);
            return Optional.of(new ParseResult<>(operator, begin_offset + 1));
        }

        log.error("Бинарный оператор не найден");
        return Optional.empty();
    }
}
