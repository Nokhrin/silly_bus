package Calculator;

/**
 * Пример оператор "пробел" 
 *
 * <setParam1> ::= "set_param" <boolean>
 *
 * должна быть функция
 *
 * Optional<Result<SetParam1>> parseSetParam1( String source, int offset )
 * record SetParam1( boolean value )
 *
 * функция должны выполнить два действия
 *
 * 1. в позиции offset убедиться в наличии текста "set_param"
 * 1.err - если нет, вернуть Optional.empty
 * 1.ok - перейти к следующему шагу
 *
 * 2. в позиции offset + "set_param".length() - вызвать функцию r1 = parseBoolean( source,  offset + "set_param".length() )
 * 2.err - если нет, вернуть Optional.empty
 * 2.ok - если да, вернуть
 * Result value=new SetParam1( полезный результат parseBoolean - r1.value ), beginOffset = offset, endOffset = r1.endOffset
 *
 * обрати внимание на offset - внимательно, что откуда и куда
 *
 * -------------
 *
 * обрати внимание на последовательность действий - она совпадает с синтаксисом/правилом
 */
public class ParseWhitespace {
}
