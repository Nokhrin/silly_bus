package com.parser.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Результат успешного парсинга.
 * Создает результат парсинга
 * Time: O(1), Space: O(1)
 * Ошибка парсинга -> Optional.empty()
 *
 * @param <A> тип распарсенного значения
 */
public record ParseResult<A>(A value, int end_offset) {
    static final Logger log = LoggerFactory.getLogger(ParseResult.class);

    /**
     * Изменяет тип значения результата парсинга,
     * пример: List <String> -> List <Boolean>
     * применяется к каждому элементу Function<A,B>
     * не изменяет смещение
     *
     * @param function функция для приведения результата парсинга
     * @param <B> результирующий тип
     * @return результат парсинга, приведенный к запрошенному типу
     */
    public <B> ParseResult<B> map(Function<A, B> function) {
        log.debug("Выполняется применение функции {}", function);
        return new ParseResult<>(function.apply(value()), end_offset());
    }

}
