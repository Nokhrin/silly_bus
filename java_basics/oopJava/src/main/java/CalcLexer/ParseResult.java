package CalcLexer;

//region ParseResult record
/**
 * Объект результата парсинга
 *
 * Применяю record - контейнер неизменяемых данных
 * поля immutable, как если бы объявил класс с private final
 * методы equals, hashCode и toString создаются по умолчанию
 *
 * Применяется обобщение для типа значения лексемы
 *  - допускается Integer, Decimal для чисел, String для операторов, пробелов
 */
public record ParseResult<T> (
        T value,
        int start,
        int end
) { }
//endregion
