package calc_parser;
//region ParseResult record
/**
 * Результат парсинга
 */
public record ParseResult<T> (
        T value,
        int start,
        int end
) { }
//endregion