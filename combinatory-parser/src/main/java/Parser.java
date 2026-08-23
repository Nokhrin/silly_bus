import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface Parser<T> {
    Optional<Parsed<T>> apply(String source, int offset);


    default <R> Parser<Tuple<T, R>> plus(Parser<R> right) {
        return this.flatMap(leftParsed -> {
            // внутри mapper
            //this - результат парсера слева
            //right - парсер справа

            //возврат mapper - парсер кортежа
            return (String source, int offset) -> {
                //правый парсер
                Optional<Parsed<R>> rightParsed = right.apply(source, offset);

                if (rightParsed.isEmpty()) {
                    return Optional.empty();
                }

                return Optional.of(
                        new Parsed<>(
                                new Tuple<>(leftParsed.value(), rightParsed.get().value()),
                                rightParsed.get().offset()
                        ));
            };
        });
    }

    default <R> Parser<R> flatMap(Function<Parsed<T>, Parser<R>> mapper) {
        return (String source, int offset) -> {
            Optional<Parsed<T>> leftResult = this.apply(source, offset);

            if (leftResult.isEmpty()) {
                return Optional.empty();
            }

            Parser<R> nextParser = mapper.apply(leftResult.get());

            return nextParser.apply(source, leftResult.get().offset());
        };
    }

    default <R> Parser<Either<T, R>> or(Parser<R> right) {
        return (String source, int offset) -> {
            Optional<Parsed<T>> leftResult = this.apply(source, offset);
            //or => или успех, или неудача левого => вызвать правый
            if (leftResult.isEmpty()) {
                Optional<Parsed<R>> rightResult = right.apply(source, offset);
                if (rightResult.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(new Parsed<>(
                        Either.right(rightResult.get().value()),
                        rightResult.get().offset()));
            }
            return Optional.of(new Parsed<>(
                    Either.left(leftResult.get().value()),
                    leftResult.get().offset()));
        };
    }

    /**
     * получает значение типа T
     * вызывает преобразование T->R
     * возвращает значение типа R
     * не изменяет offset
     */
    default <R> Parser<R> map(Function<T, R> transformer) {
        return ((source, offset) -> {
            Optional<Parsed<T>> result = this.apply(source, offset);
            if (result.isEmpty()) return Optional.empty();
            Parsed<T> parsed = result.get();
            R parsedTransformed = transformer.apply(parsed.value());
            return Optional.of(new Parsed<>(parsedTransformed, parsed.offset()));
        });
    }

    /**
     * Пропускает левое выражение
     * Сохраняет offset
     */
    default <R> Parser<R> skipLeft(Parser<R> right) {
        return this.plus(right)
                .map(Tuple::right);
    }

    /**
     * Пропускает правое выражение
     * Сохраняет offset
     */
    default <R> Parser<T> skipRight(Parser<R> right) {
        return this.plus(right)
                .map(Tuple::left);
    }

    /**
     * Комбинатор-итератор
     * Выполняет повторения,
     * накапливает результаты в список,
     * сохраняет смещение
     * Реализует грамматику {min,max}
     * Ищет все совпадения до max (жадность) или до отказа парсера
     */
    default Parser<List<T>> repeat(int min, int max) {
        if (min > max || min < 0) throw new IllegalArgumentException("Количество повторов указано некорректно");
        return ((source, offset) -> {
            List<T> results = new ArrayList<>();
            int currentOffset = offset;

            for (int i = 0; i < max; i++) {
                Optional<Parsed<T>> result = this.apply(source, currentOffset);
                if (result.isEmpty()) break;
                results.add(result.get().value());

                if (result.get().offset() == currentOffset) {
                    throw new IllegalStateException("Зацикливание - успешный парсинг без смещения");
                }
                currentOffset = result.get().offset();
            }

            if (results.size() < min) return Optional.empty();
            return Optional.of(new Parsed<>(results, currentOffset));
        });
    }

    /**
     * Грамматика *
     */
    default <R> Parser<List<T>> zeroOrMore() {
        return this.repeat(0, Integer.MAX_VALUE);
    }

    /**
     * Грамматика +
     */
    default <R> Parser<List<T>> oneOrMore() {
        return this.repeat(1, Integer.MAX_VALUE);
    }

    /**
     * Грамматика ?
     */
    default <R> Parser<Optional<T>> optionally(){
        return (source, offset) -> {
            Optional<Parsed<T>> result = this.apply(source, offset);
            if (result.isEmpty()) return Optional.of(new Parsed<>(Optional.empty(), offset));
            return Optional.of(new Parsed<>(
                    Optional.of(result.get().value()), result.get().offset()));
        };
    }

    static Logger LOGGER = LoggerFactory.getLogger("ParserDebug");
    /**
     * Формирует строку логирования.
     */
    default <R> Parser<T> debug(String parserName){
        return (source, offset)->{
            LOGGER.debug(">>> Парсер: {}: строка={}, стартовое смещение={}", parserName, source, offset);
            Optional<Parsed<T>> result = this.apply(source, offset);

            if (result.isEmpty()){
                LOGGER.debug("<<< Выполнен: {}: ОШИБКА парсинга на смещении {}", parserName, offset);
            }
            LOGGER.debug("<<< Выполнен: {}: УСПЕХ парсинга, значение: {} на смещении: {}", parserName, result.get().value(), offset);
            return result;
        };
    }

    /**
     * Комбинатор однотипных альтернатив
     */
    default Parser<T> alt(Parser<T> other){
        return (source, offset) -> {
            Optional<Parsed<T>> leftResult = this.apply(source, offset);
            if (leftResult.isPresent()) return leftResult;
            return other.apply(source, offset);
        };
    }

    /**
     * Возвращает лексему без пробелов
     */
    default Parser<T> lexeme(){
        Parser<String> ws = Parsers.whitespaces();
        return ws.skipLeft(this.skipRight(ws));
    }
}
