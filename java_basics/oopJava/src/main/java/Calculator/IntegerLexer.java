package Calculator;

import java.util.Optional;

/**
 * Напиши функцию парсинга числа , целое
 * 
 * eBNF
 * ===
 * 1 знак или +, или -
 * 1+ цифра - строка с цифрой от 0 до 9
 * число - знак? цифры в количестве от 1 и более
 * 
 * sign   ::= "+" | "-"
 * digit  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * digits ::= [sign] digit { digit }*
 * 
 * требования к парсеру  
 *     Не парсит пустые строки 
 *     Умеет обрабатывать  +,  -
 *          - >1 знака подряд => ошибка?
 *     Останавливается на первом неправильном символе 
 *     Правильно работает с границами  int
 *     Использует  Optional
 *     методы public static
 *     обобщенные типы для значения, полученного парсингом
 *      - Boolean для знака, Integer для числа
 */
public class IntegerLexer {

    
    public static void parseSignedInt(Optional<String> source, Optional<ParseResult> result) {
        
    }

    /**
     * Парсинг знака
     * sign       ::= "+" | "-"
     * 
     * Используй результат Optional<ParseResult> для расчета offset
     *  ParseResult содержит 
     *      значение, 
     *      первый (включительно),
     *      последний (исключительно) индексы значения
     * Инкремент запрещен
     * 
     * используй комментирование регионов кода
     * 
     * @param source
     * @return
     */
    public static Optional<ParseResult<Boolean>> parseSign(String source, int start) {
        //region "+" | "-"
        return null;
        //endregion
    }
}
