package CalcParser;

import static CalcParser.NaryExpression.parseNaryExpression;

public class Main {

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
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[left=BinaryExpression[left=NumValue[value=1.0], op=ADD, right=NumValue[value=2.0]], op=ADD, right=NumValue[value=3.0]], op=ADD, right=NumValue[value=4.0]], start=0, end=13]]

        // =========================

        // 5 - 2 * 3
        //без приоритетов 
//        System.out.println(NaryExpressionNoPriority.parseNaryExpression("5 - 2 * 3", 0));
        //                       -                       
        // 5.0                                             2.0  
        //                                                                 *
        //                        -                                                            3.0
        //Optional[ParseResult[value=BinaryExpression[left=BinaryExpression[
        //  left=NumValue[value=5.0], op=SUB, right=NumValue[value=2.0]
        //  ], 
        //                                                               op=MUL, 
        //                                                                        right=NumValue[value=3.0]], 
        // start=0, end=9]]
        //получаем косяк: (( 5 - 2 ) * 3 )
        //должно быть: (5 - (2 * 3))

        // =========================

        // 5 - 2 * 3
        //с приоритетами 
        System.out.println(parseNaryExpression("5 - 2 * 3", 0));
        //                                 -                                   
        //                      5.0                                               *   
        //                                                              2.0                 3.0
        //Optional[ParseResult[value=BinaryExpression[
        // left=NumValue[value=5.0], op=SUB, 
        //                          right=BinaryExpression[
        //                                           left=NumValue[value=2.0], op=MUL, 
        //                                                              right=NumValue[value=3.0]
        //                                                 ]
        //                                           ], 
        //  start=0, end=9]]
    }

}
