package Calculator;

/** todo

 * Пример - примитивная конструкция совпадения 
 *
 * <true> ::= "true"
 *
 * задано правило true
 * соответственно должна быть функция
 *
 * Optional<Result<Object>> parseTrue( String source, int offset )
 *
 * функция должна проверить что в позиции offset исходника source присутствует текст "true"
 * и если так, то должна вернуть Result , где value = любое значение, beginOffset = offset, endOffset = offset + "true".length()
 * 
 * 
 * Пример Оператор или - Вертикальная черта
 *
 * <boolean> ::= "true" | "false"
 *
 * должна быть функция
 *
 * Optional<Result<Boolean>> parseBoolean( String source, int offset )
 *
 * функция должна проверить что в позиции присутствует true или offset, и если так, то возвращает
 * Result value = true иди false, beginOffset = offset, endOffset = offset + (4 или 5)
 * 
 */
public class ParseBoolean {
}
