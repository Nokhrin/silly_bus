package CalcParser;

import java.util.Optional;

import static CalcParser.Parser.parseAddSubExpression;

public class Main {
public static void main(String[] args) {
        Optional<ParseResult<Expression>> exp = parseAddSubExpression("9 / ( 6 - 3 * 3 )", 0);
        System.out.println(exp);
        // Optional[ParseResult[value=BinaryExpression[left=
    // NumValue[value=9.0], op=DIV, 
    //                              right=BinaryExpression[
    //                                      left=NumValue[value=6.0], op=SUB, 
    //                                                    right=BinaryExpression[
    //                                                              left=NumValue[value=3.0], op=MUL, right=NumValue[value=3.0]]]], start=0, end=17]]
    //                9.0               /
    //                                                      6.0           -
    //                                                                          3.0         *           3.0

    System.out.println(exp.get().value().evaluate()); // -3.0

}

}
