package CalcLexer;
import java.util.Optional;

//region ParseResult record
/**
 * Результат парсинга — контейнер для неизменяемых данных.
 *
 * <p>Предназначен для возврата результата парсинга из методов, которые могут не найти ожидаемое значение при парсинге, вследствие - вернуть null
 *
 * <p>Используется в парсерах для возврата:
 * <ul>
 *   <li>парсинга числа → {@code ParseResult<Integer>}</li>
 *   <li>парсинга оператора → {@code ParseResult<Operation>}</li>
 *   <li>парсинга выражения → {@code ParseResult<Expression>}</li>
 *   <li>парсинга пробелов → {@code ParseResult<String>}</li>
 * </ul>
 *
 * @param <T> тип результата парсинга: может быть число, оператор, выражение, пробелы и т.д.
 * @param value    прочитанное значение (например, число, оператор, выражение). Не может быть null.
 * @param start    индекс в исходной строке, с которого начинается чтение значения (включительно).
 * @param end      индекс в исходной строке, на котором заканчивается чтение значения (исключительно).
 *                 <p>Значение <code>end</code> всегда больше или равно <code>start</code>.
 *
 * @see Optional
 * @see ParseResult#value()
 * @see ParseResult#start()
 * @see ParseResult#end()
 */
public record ParseResult<T> (
        T value,
        int start,
        int end
) { }
//endregion
