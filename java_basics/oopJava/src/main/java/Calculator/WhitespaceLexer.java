package Calculator;

import java.util.Optional;

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
public class WhitespaceLexer {
    // строка для поиска
    private final String source;
    // смещение === индекс символа в строке
    private int offset;
    
    // конструктор принимает исходную строку
    public WhitespaceLexer(String source) {
        this.source = source;
        this.offset = 0;
    }

    public String getSource() {
        return source;
    }

    public int getOffset() {
        return offset;
    }
    
    // результат может быть null, 
    // контейнер Optional подходит для ситуаций, когда лексема не распознана или не найдена
    public Optional<ParseResult<String>> parseFrom(int start) {
        // todo
        return null;
    }
}
