package com.parser.combinator;

import com.parser.atomic.WhitespaceParser;
import com.parser.core.ParseResult;
import com.parser.core.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Комбинатор парсеров
 * Реализует грамматику {A} | [A]
 * A - внутренний парсер объекта типа A
 * min, max - минимальное и максимальное количество повторов внутреннего парсера
 * Выбрасывает исключение на эпсилон-цикл
 */
public class ListParser<A> implements Parser<List<A>> {
    private static final Logger logger = Logger.getLogger(ListParser.class.getName());
    private final Parser<A> parser;
    private final int min;
    private final int max;

    public ListParser(Parser<A> parser, int min, int max) {
        if (min < 0) {
            throw new IllegalArgumentException("min должен быть >= 0");
        }
        if (max < min) {
            throw new IllegalArgumentException("max должен быть >= min");
        }
        this.parser = parser;
        this.min = min;
        this.max = max;
    }

    /**
     * Повторяет вызов парсера
     * @param source входная строка
     * @param begin_offset смещение начала парсинга
     * @return
     */
    @Override
    public Optional<ParseResult<List<A>>> parse(String source, int begin_offset) {
        List<A> resultList = new ArrayList<>();
        int offset = begin_offset;
        int repeatsCount = 0;

        while (repeatsCount < max) {
            Optional<ParseResult<A>> resultOptional = parser.parse(source, offset);

            if (resultOptional.isEmpty()) {
                logger.log(Level.FINE, "Не удалось выполнить парсинг по смещению {0}", offset);
                break;
            }

            int currentOffset = resultOptional.get().end_offset();

            if (currentOffset == offset) {
                throw new IllegalStateException(
                        "Зацикливание: успешный парсинг без смещения"
                );
            }

            resultList.add(resultOptional.get().value());
            offset = currentOffset;
            repeatsCount++;

        }
        if (repeatsCount < min) {
            logger.log(Level.FINE, "Не удалось найти минимум совпадений");
            return Optional.empty();
        }
        return Optional.of(ParseResult.of(resultList, offset));
    }
}
