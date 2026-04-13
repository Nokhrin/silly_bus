package com.parser.core;

import com.parser.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

/**
 * Функциональный интерфейс парсера.
 *
 * @param <A> тип результата успешного парсинга
 */
public interface Parser<A> {
    static final Logger log = LoggerFactory.getLogger(Parser.class);

    /**
     * Выполняет парсинг
     * @param source входная строка
     * @param begin_offset смещение начала парсинга
     * @return Optional.empty() если парсинг неудачен, иначе ParseResult со значением и новым offset
     *
     * Time: зависит от реализации, Space: O(1) для атомарных парсеров
     */
    Optional<ParseResult<A>> parse(String source, int begin_offset);

    /**
     * Применяет функцию к результату парсинга
     * @param function
     * @param <B>
     * @return результат парсинга типа, возвращенного примененной функцией
     */
    default <B> Parser<B> map(Function<A, B> function) {
        return (src, offset) -> {
            Optional<ParseResult<A>> parseResultOptional = this.parse(src, offset);
            if (parseResultOptional.isPresent()) {
                log.info("Успешный парсинг в смещении {}", offset);
                return Optional.of(parseResultOptional.get().map(function));
            }
            return Optional.empty();
        };
    }

    /**
     * Фильтрует, уменьшает, увеличивает содержимое исходного контейнера
     * @param function
     * @param <B>
     * @return
     */
    default <B> Parser<B> flatMap(Function<A, Optional<B>> function) {

        return (src, offset) -> {
            Optional<ParseResult<A>> parseResultOptional = this.parse(src, offset);
            if (parseResultOptional.isEmpty()) {
                log.info("Парсинг вернул пустое значение");
                return Optional.empty();
            }

            log.info("Успешный парсинг в смещении {}", offset);
            ParseResult<A> parseResult = parseResultOptional.get();
            Optional<B> resultModified = function.apply(parseResult.value());

            if (resultModified.isPresent()) {
                log.info("Успешное применение функции {} к значению {}", function, offset);
                return Optional.of(ParseResult.of(resultModified.get(), parseResult.end_offset()));
            }

            log.debug("Применение функции вернуло пустой результат");
            return Optional.empty();
        };

    }

}