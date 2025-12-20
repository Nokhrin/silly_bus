package CalcParser;

import java.util.Optional;

import static CalcParser.Parser.parseAddSubExpression;

public class Main {
    public static void main(String[] args) {
        Optional<ParseResult<Expression>> exp;
        
        exp = parseAddSubExpression("-1 + 2", 0);
        System.out.println(exp.get().value().evaluate());
        //
        exp = parseAddSubExpression("--1 + 2", 0);
        System.out.println(exp);
        //
        exp = parseAddSubExpression("--1 + +2", 0);
        System.out.println(exp);
        //
    }

}
