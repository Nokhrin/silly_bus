package com.parser.core;

import com.parser.combinator.ListParser;
import com.parser.combinator.PlusParser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Функциональный интерфейс парсера.
 *
 * @param <A> тип результата успешного парсинга
 */
public interface Parser<A> {
    /**
     * Выполняет парсинг
     *
     * @param source       входная строка
     * @param begin_offset смещение начала парсинга
     * @return Optional.empty() если парсинг неудачен, иначе ParseResult со значением и новым offset
     * <p>
     * Time: зависит от реализации, Space: O(1) для атомарных парсеров
     */
    Optional<ParseResult<A>> parse(String source, int begin_offset);

    /**
     * Применяет функцию к результату парсинга
     *
     * @param function
     * @param <B>
     * @return результат парсинга типа, возвращенного примененной функцией
     */
    default <B> Parser<B> map(Function<A, B> function) {
        return (src, offset) -> {
            Optional<ParseResult<A>> parseResultOptional = this.parse(src, offset);
            return parseResultOptional.map(aParseResult -> aParseResult.map(function));
        };
    }

    /**
     * Фильтрует, уменьшает, увеличивает содержимое исходного контейнера
     * <p>
     * Пример:
     * спарсили число 123, результат парсинга корректен
     * требуется получить только четные числа
     * данный метод делает значение 123 некорректным, то есть, фильтрует
     *
     * @param function
     * @param <B>
     * @return
     */
    default <B> Parser<B> flatMap(Function<A, Optional<B>> function) {

        return (src, offset) -> {
            Optional<ParseResult<A>> parseResultOptional = this.parse(src, offset);
            if (parseResultOptional.isEmpty()) {
                return Optional.empty();
            }

            ParseResult<A> parseResult = parseResultOptional.get();
            Optional<B> resultModified = function.apply(parseResult.value());

            if (resultModified.isPresent()) {
                return Optional.of(new ParseResult<>(resultModified.get(), parseResult.end_offset()));
            }

            return Optional.empty();
        };

    }

    /**
     * Возвращает последовательность двух парсеров
     * Реализует логику Sequence: A B
     */
    default <B> Parser<Tuple2<A, B>> plusTupleExample(Parser<B> secondParser) {
        return (src, offset) -> {
            Optional<ParseResult<A>> firstResultOptional = this.parse(src, offset);
            if (firstResultOptional.isEmpty()) {
                return Optional.empty();
            }
            ParseResult<A> firstParseResult = firstResultOptional.get();

            Optional<ParseResult<B>> secondResultOptional = secondParser.parse(src, firstParseResult.end_offset());
            if (secondResultOptional.isEmpty()) {
                return Optional.empty();
            }
            ParseResult<B> secondParseResult = secondResultOptional.get();

            Tuple2<A, B> result = new Tuple2<>(firstParseResult.value(), secondParseResult.value());
            return Optional.of(new ParseResult<>(result, secondParseResult.end_offset()));
        };
    }

    /**
     * Возвращает последовательность двух парсеров
     * Реализует логику Sequence: A B
     */
    default <B> Plus<A, B> plus(Parser<B> secondParser) {
        return new PlusParser<>(this, secondParser);
    }

    /**
     * Повторяет парсер от min до max раз
     * Реализует грамматику {}
     */
    default Parser<List<A>> repeat(int min, int max) {
        return new ListParser<>(this, min, max);
    }

    /**
     * Повторяет парсер от 0 до 1 раз
     * Реализует грамматику []
     * == repeat(0, 1)
     */
    default Parser<Optional<A>> optional() {
        Parser<List<A>> parser = this.repeat(0, 1);
        return parser.map(resultList -> {
            if (resultList.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(resultList.getFirst());
        });
    }

    /**
     * Выполняет операцию ИЛИ.
     * Реализует грамматику альтернативы `A | B`
     * 1. Парсинг A, при успехе - возвращает A, при неудаче парсит B
     * 2. Парсинг B, при успехе - возвращает B, при неудаче возвращает empty
     */
    default <B> Parser<Either<A, B>> or(Parser<B> parserB) {
        return (source, begin_offset) -> {
            Optional<ParseResult<A>> resultA = this.parse(source, begin_offset);
            if (resultA.isPresent()) {
                return Optional.of(
                        new ParseResult<>(Either.left(resultA.get().value()), resultA.get().end_offset())
                );
            }

            Optional<ParseResult<B>> resultB = parserB.parse(source, begin_offset);
            if (resultB.isPresent()) {
                return Optional.of(
                        new ParseResult<>(Either.right(resultB.get().value()), resultB.get().end_offset())
                );
            }
            return Optional.empty();
        };
    }
}