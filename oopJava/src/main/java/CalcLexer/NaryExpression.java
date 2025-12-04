package CalcLexer;
import static CalcLexer.Parsers.*;
import java.util.Optional;


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
     *      
     * <p>
     * <p>
     * Применяемые принципы:
     * <p>
     * Левоассоциативный порядок: 1 + 2 + 3 + 4 -> (1 + 2) + 3 + 4 -> ((1 + 2) + 3) + 4
     * Операторы: +, -, *, /
     * Целые числа - десятичные, без ведущих нулей (т.е. 0 - допустимо, но 01, 00 - недопустимы)
     * Знак - отрицательному числу предшествует `-`
     * Пробелы - разделяют оператор и операнд - в начале и конце строки допускаются
     * <p>
     * <p>
     * Задача:
     *            8) меняем грамматику / синтаксис так
     *            addSub ::= mulDiv { mulDivOp mulDiv }  
     *              //вопрос - выражение сложения/вычитания определение через выражение умножения/деления приоритет mulDiv выше
     *              опечатка? 
 *                  проверь трактовку в строке 49
     *
     *            mulDiv ::= number { addSubOp number }
     *              //вопрос - опечатка в определении? 
 *                  проверь трактовку в строке 50
     *            
     *            mulDivOp ::= '+' | '-'
     *            addSubOp ::= '*' | '/'
     *            тут я не использую угловые скобки <rule> и опустил [<ws>]
     *            получаем грамматику с поддержкой приоритетов
     *            5 - 2 * 3
     *            расчет корректный получается
     * <p>
     * <p>
     * <p>
     * Синтаксическая грамматика в нотации eBNF:
     * <p>
     *  add_sub_expression ::= mul_div_expression { add_sub_operator mul_div_expression }
     *  mul_div_expression ::= num_value { mul_div_operator num_value }
     *  mul_div_operator ::= "*" | "/"
     *  add_sub_operator ::= "+" | "-"
     *  num_value ::= [sign] digit {digit}
     *  digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  sign ::= "+" | "-"
     *  ws ::= " " | "\t" | "\n" | "\r"
     *  
     *  todo - понимание
     *      add_sub_expression ::= mul_div_expression { [ws] add_sub_operator [ws] mul_div_expression }
     *          //выражение операции сложения/вычитания:
     *              1 выражение операции умножения/деления - это расположение определяет приоритет - приоритет умножение/деление вы сложение/вычитание
     *                  0 или более выражений, состоящих из
     *                      0 или 1 пробельный символ, 
     *                      1 оператор сложения/вычитания,
     *                      0 или 1 пробельный символ, 
     *                      1 операции умножения/деления,
     *      mul_div_expression ::= num_value { [ws] mul_div_operator [ws] num_value }
     *          //выражение операции умножения/деления: 
     *          //ПОСЛЕДОВАТЕЛЬНОСТЬ чисел, разделенных * и / , без учета скобок группировки
     *              1 число, 
     *                  0 или более выражений, состоящих из
     *                      0 или 1 пробельный символ,
     *                      1 оператор умножения/деления,
     *                      0 или 1 пробельный символ,
     *                      1 число
     *      num_value ::= [sign] digit {digit}
     *          //число с необязательным знаком: [sign] - 0 или 1 знак sign, digit - одна цифра, {digit} - 0 и более цифр
     *      sign ::= "+" | "-"
     *          //знак числа - символ sign обозначает один из терминалов: + или -
     *      mul_div_operator ::= "*" | "/"
     *          //оператор умножения/деления: символ "*" или "/" 
     *      add_sub_operator ::= "+" | "-"
     *          //оператор сложения/вычитания: символ "+" или "-"
     *      digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *          //цифра - символ digit обозначает один из терминалов: цифры от 0 до 9 включительно
     *      ws ::= " " | "\t" | "\n" | "\r" 
     *          //пробельные символы - символ ws обозначает один из терминалов " " или "\t" или "\n" или "\r"
     *  
     *  todo - понимание соответствует действительности?
     *  
     *  
     *  todo
     *      1. "2" без явного знака - символы "1", "+", "2" считаны успешно
     *      System.out.println(parseNaryExpression("1 + 2", 0));
     *      Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=5]]
     *      ---
     *      2. "+2" с явным знаком - символы "1", "+", "2" считаны успешно
     *      System.out.println(parseNaryExpression("1 + +2", 0));
     *      Optional[ParseResult[value=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], start=0, end=6]]
     *      ---
     *      3. "++2" с избыточным знаком - символ "1" считан успешно,
     *               символы "+", "2" не считаны
     *          - утверждение: парсинг реализован по требуемой грамматике, 
     *            - при нарушении грамматики возвращается empty()
     *            - empty() возвращается каждым методом парсинга
     *            - при ошибке парсинга как в примере "1 + ++2" найти ошибку просто, 
     *            - в выражении с большим числом символов "1 + 2 + 2 + 2 + 2 + 2 + 2 + 2 + +2 + -2 ++-2 + 2 + 2 + 2" найти ошибку будет сложнее
     *            - ВОПРОС: следует ли реализовать вывод сообщения об ошибке в методах парсинга?
     *              - цель - показать пользователю на каком индексе произошел сбой, упростить отладку
     *          - следует ли реализовать сообщение об ошибке парсинга?
     *      System.out.println(parseNaryExpression("1 + ++2", 0));
     *      Optional[ParseResult[value=NumValue[value=1.0], start=0, end=4]]
     *      ---
     *  todo
     *      задача не предусматривает скобки как инструмент группировки выражений
     *      отсутствие скобок в постановке задачи сделано намеренно?
     * @see Parsers
     */
public class NaryExpression {

    /**
     * Парсинг выражения с учетом свойств операций:
     *  - приоритета операций - приоритет * / выше + -
     *  - левоассоциативности операций - 1 + 2 + 3 -> (1 + 2) + 3
     * <p>
     * синтаксис:
     *  add_sub_expression ::= mul_div_expression { [ws] add_sub_operator [ws] mul_div_expression }
     *  <p>
     * вспомогательное разделение синтаксиса:
     * add_sub_expression ::= first_expression { second_expression }
     * first_expression ::= mul_div_expression
     * second_expression ::= { [ws] add_sub_operator [ws] mul_div_expression }
     * <p></p>
     * @param source исходная строка  
     * @param start  стартовый индекс  
     * @return ParseResult(Expression) или Optional.empty(), если парсинг не удался
     */
    public static Optional<ParseResult<Expression>> parseNaryExpression(String source, int start) {
        // проверка входных данных
        if (source.isEmpty() || start < 0 || start >= source.length()) {
            return Optional.empty();
        }
        
        //first_expression
        Optional<ParseResult<Expression>> firstExpressionOptional = parseMulDivExpression(source, start);
        if (firstExpressionOptional.isEmpty()) { return Optional.empty(); }
        
        Expression firstExpression = firstExpressionOptional.get().value();
        int offset = firstExpressionOptional.get().end();
        
        //second_expression
        while (offset < source.length()) {
            //ws
            Optional<ParseResult<String>> ws1 = parseWhitespace(source, offset);
            if (ws1.isPresent()) { offset = ws1.get().end(); }
            
            //add_sub_operator
            Optional<ParseResult<Operation>> opOpt = parseOperation(source, offset);
            if (opOpt.isEmpty()) { break; }
            Operation op = opOpt.get().value();
            offset = opOpt.get().end();
            
            //ws
            Optional<ParseResult<String>> ws2 = parseWhitespace(source, offset);
            if (ws2.isPresent()) { offset = ws2.get().end(); }
            
            //mul_div_expression
            Optional<ParseResult<Expression>> secondExpressionOptional = parseMulDivExpression(source, offset);
            if (secondExpressionOptional.isEmpty()) { break; }
            
            Expression secondExpression = secondExpressionOptional.get().value();
            offset = secondExpressionOptional.get().end();

            //накапливаем значение second_expression в first_expression
            firstExpression = new BinaryExpression(firstExpression, op, secondExpression);
        }

        return Optional.of(new ParseResult<>(firstExpression, start, offset));
        
    }

    /**
     * получаем грамматику с поддержкой приоритетов
     * 5 - 2 * 3
     * ----------
     *                *
     *            2       3
     *      -
     * 5
     * 
     * @param args
     */
    public static void main(String[] args) {
        /*
        получаем лево ассоциированное дерево 
                   1 + 2 + 3 + 4
                   ----------------
                   1                          2                                                         
                                  +                         +                                  
                                                                                      +                
                                                                              3                   4
         */
        System.out.println(parseNaryExpression("1 + 2 + 3 + 4", 0));
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[left=BinaryExpression[left=
        // NumValue[value=1.0], op=ADD, right=
        //                      NumValue[value=2.0]], op=ADD, right=
        //                                                  NumValue[value=3.0]], op=ADD, right=
        //                                                                      NumValue[value=4.0]], start=0, end=13]]
        
        // 5 - 2 * 3
        //без приоритетов 
        System.out.println(NaryExpressionNoPriority.parseNaryExpression("5 - 2 * 3", 0));
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

        // 5 - 2 * 3
        //с приоритетами 
        System.out.println(parseNaryExpression("5 - 2 * 3", 0));
        //               5.0                                                                                  
        //                          -                                   
        //                                                    2.0                  
        //                                                                *
        //                                                                                3.0
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[left=
        // NumValue[value=5.0], 
    //                          op=SUB, 
//                                      right=NumValue[value=2.0]], 
//                                                                  op=MUL, 
        //                                                                  right=NumValue[value=3.0]], start=0, end=9]]
    }

}
