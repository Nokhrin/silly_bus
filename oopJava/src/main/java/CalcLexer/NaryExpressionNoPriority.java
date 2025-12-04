package CalcLexer;

import java.util.Optional;

import static CalcLexer.Parsers.*;

public class NaryExpressionNoPriority {
    /**
     * Парсер многоместных операций
     * <p>
     * Требования:
     * <p>
     *  Входной аргумент: 
     *      строка, представляющая 
     *      <number> { [<ws>] <op> [<ws>] <number> }
     *      толкование:
     *      одно число, за которым следует 0 и более раз выражение 
     *      `[<ws>] <op> [<ws>] <number>`, которое содержит:
     *      0 или 1 пробел|табу, один оператор, 0 или 1 пробел|табу, одно число
     *
     *      todo - вопрос
     *      толкование верно?
     * <p>
     * <p>
     * Применяемые прицепы:
     * <p>
     * Левоассоциативный порядок: 1 + 2 + 3 + 4 -> (1 + 2) + 3 + 4 -> ((1 + 2) + 3) + 4
     * Операторы: +, -, *, /
     * Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     * Знак - отрицательному числу предшествует `-`
     * Пробелы - разделяют оператор и операнд - в начале и конце строки допускаются
     * <p>
     * Задача:
     * - парсить любое количество операций
     * - использовать левоассоциативность
     * <p>
     * 
     *     
     * <p>
     *      todo - вопросы
     *      
     *      -1-
     *      в постановке
     *      <number> { [<ws>] <op> [<ws>] <number> }
     *      меня смущает 0 или раз для ws - [<ws>] -  в { [<ws>] <op> [<ws>] <number> }
     *      по условию число пробельных символов 0 и более, то есть {<ws>}
     *      
     *      мне кажется точнее запись { {<ws>} <op> {<ws>} <number> }
     *      корректна ли такая запись? 
     *      
     *      
     *      -2-
     *     использую `wc` - whitespace character
     *     вместо `ws` - whitespace
     *     считаю, `wc` семантически точнее, так как описывает множество пробельных символов
     *      корректно ли такое определение?
     *
     * 
     *      -3-
     *      следует ли дополнить требования условиями:
     *      - для пробельных символов 0 и более вхождений
     *      - в число пробельных лексем добавил /n, /r
     *      ?
     * <p>
     * <p>
     * Синтаксическая грамматика в нотации eBNF:
     * <p>
     * <p>
     *     вариант для обсуждения
     * <p>
     *  nary_expression ::= {wc} num_value { {wc} op {wc} num_value } {wc}
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  wc ::= " " | "\t" | "\n" | "\r"
     * <p>
     * <p>
     *     вариант по условию - для решения
     * <p>
     *  nary_expression ::= num_value { [<ws>] <op> [<ws>] num_value }
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  op ::= "+" | "-" | "*" | "/"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t"
     *  
     *  // пояснение понимания: терминалы в этом выражении -
     *      все символы в правилах digit, op, sign, ws: " ", "\t", "+", "5", ...
     */

    /**
     * Левоассоциативный парсинг выражения по правилу
     * nary_expression ::= num_value { [<ws>] <op> [<ws>] num_value }
     * 
     * nary_expression ::= expression1 { expression2 }
     * expression1 ::= num_value
     * expression2 ::= { [<ws>] <op> [<ws>] num_value }
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        
        // num_value
        var firstNum = parseNumber(source, start);
        if (firstNum.isEmpty()) { return Optional.empty(); }
        
        // expression1 ::= num_value
        Expression expression1 = firstNum.get().value();
        int offset = firstNum.get().end();
        
        // expression2 ::= { [<ws>] <op> [<ws>] num_value }
        // парсинг в цикле до получения невалидного символа или окончания строки
        while (true) {
            // ws1
            var wsOpt1 = parseWhitespace(source, offset);
            if (wsOpt1.isEmpty()) {
                break;
            }
            offset = wsOpt1.get().end();

            // op
            var opOpt = parseOperation(source, offset);
            if (opOpt.isEmpty()) {
                break;
            }
            Operation op = opOpt.get().value();
            offset = opOpt.get().end();

            // ws2
            var wsOpt2 = parseWhitespace(source, offset);
            if (wsOpt2.isEmpty()) {
            } else {
                offset = wsOpt2.get().end();
            }

            // num_value
            var secondNum = parseNumber(source, offset);
            if (secondNum.isEmpty()) {
                break;
            }

            // expression2
            Expression expression2 = new BinaryExpression(expression1, op, secondNum.get().value());
            expression1 = expression2;  // смещаем результат влево, левоассоциативность
            offset = secondNum.get().end();
        }

        return Optional.of(new ParseResult<>(expression1, start, offset));
    }

    /**
     * получаем лево ассоциированное дерево
     * 1 + 2 + 3 + 4
     * ----------------
     *           +
     *       +     4
     *   +     3
     * 1    2
     *
     *
     * пишем тест: 5 - 2 * 3
     * получаем косяк: (( 5 - 2 ) * 3 )
     * должно быть: (5 - (2 * 3))
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(parseNaryExpression("1", 0));
        //Optional[ParseResult[value=NumValue[value=1.0], start=0, end=1]]
        System.out.println(parseNaryExpression("1 + 2", 0));
        //Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=5]]
        System.out.println(parseNaryExpression("1 + 2 + 3", 0));
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], op=ADD, right=NumValue[value=3.0]], start=0, end=9]]
        
        /*
        получаем лево ассоциированное дерево 
                            1 + 2 + 3 + 4
                            ----------------
                            1                                      2                                                        
                                           +                                                              3                 
                                                                               +                                               4
                                                                                                                     +        
         */
        System.out.println(parseNaryExpression("1 + 2 + 3 + 4", 0));
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[left=BinaryExpression[left=
        //      NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], 
        //                                                                  op=ADD, right=NumValue[value=3.0]], 
        //                                                                                                          op=ADD, right=NumValue[value=4.0]], start=0, end=13]]
        
        //пишем тест: 5 - 2 * 3
        System.out.println(parseNaryExpression("5 - 2 * 3", 0));
        // 5 - 2 * 3
        // 5.0                                               
        //                          -
        //                                                  2.0  
        //                                                             *
        //                        -                                                            3.0
        //Optional[ParseResult[value=
        // BinaryExpression[left=BinaryExpression[left=
        // NumValue[value=5.0], op=SUB, right=NumValue[value=2.0]], op=MUL, right=NumValue[value=3.0]], start=0, end=9]]
        //получаем косяк: (( 5 - 2 ) * 3 )
        //должно быть: (5 - (2 * 3))
    }

}
