package command.parser;

//region ParseResult record
/**
 * Результат парсинга строки.
 * <p>
 * Содержит извлечённое значение, а также позиции начала и конца в исходной строке.
 *
 * @param <T> тип извлечённого значения
 * @param value извлечённое значение после парсинга
 * @param start позиция начала парсинга в исходной строке (включительно)
 * @param end позиция конца парсинга в исходной строке (исключительно)
 */
public record ParseResult<T>(
        T value,
        int start,
        int end
) {
}
//endregion
