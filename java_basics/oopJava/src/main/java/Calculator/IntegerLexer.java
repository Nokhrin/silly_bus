package Calculator;

/** todo
 * Напиши функцию парсинга числа , целое
 * 
 * 
 * Вот мое описание BNF
 * твоя задача задавать вопросы и предоставить BNF / eBNF грамматику для твой предметной области (переводы денег)
 * ---------------------------------------------
 * BNF - условный псевдо язык описания синтаксиса для искусственных языков
 * BNF - формальный, т.е. можно полностью автоматизировать
 * BNF - описывает как из букв/символов собрать дерево AST (abstract syntax tree)
 * BNF - описывает грамматические правила (1+)
 *
 * Правило имеет наименование и содержание
 * имя ::= содержание
 *
 * соответствует коду Optional<Result<X>> parseX( String source, int offset ); record Result<X>( X value, int beginOffset, int endOffset )
 *
 * beginOffset - начало (включительно) лексемы/некого составного значения в исходнике source
 * endOffset - конец (исключительно) лексемы/некого составного значения в исходнике source
 *
 * Содержание - указывает некий псевдо код который проверяет текст source в заданной позиции offset на соответствие ожиданию
 *
 * Содержание может состоять из 
 * - терминалов - ожидаемого текста, задается в кавычках
 * - операторов - различные способы проверки, в BNF всего два: пробел и вертикальная черта
 * - неТерминалов - это вызов соответствующего правила/функции парсинга для новой позиции
 * 
 * 
 * 
 */
public class IntegerLexer {
    // принимает значения String source, int offset
    // задача - извлечь целое число
    // числу может предшествовать знак +/-
    // возвращает значение, смещение, с которого началось значение и смещение, на котором закончилось значение
    // record Result<X>( X value, int beginOffset, int endOffset )
    private final String source;
    private int offset;

    public IntegerLexer(String source) {
        this.source = source;
        this.offset = 0;
    }
    
    public ParseResult parseFrom(int start) {
        if (start < 0 || start >= source.length()) {
            return new ParseResult(null, -1, -1);
        }
        
        // стартовый символ считывания
        this.offset = start;
        
        // знак
        boolean isNegative = false;
        if (source.charAt(offset) == '-') {
            isNegative = true;
            offset++;
        } else if (source.charAt(offset) == '+') {
            offset++;
        }
        // после знака следует цифра
        if (!Character.isDigit(source.charAt(offset))) {
            return new ParseResult(null, -1, -1);
        }
        
        // цифры
        while (offset < source.length() && Character.isDigit(source.charAt(offset))) {
            offset++;
        }
        
        // смещение последней цифры числа
        String numStr = source.substring(start, offset);
        Integer num = Integer.parseInt(numStr);

        return new ParseResult(num, start, offset - 1);
    }
}
