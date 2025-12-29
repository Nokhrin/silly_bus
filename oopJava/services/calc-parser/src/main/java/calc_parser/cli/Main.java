package calc_parser.cli;

import calc_parser.Expression;
import calc_parser.ParseResult;

import java.util.Optional;

import static calc_parser.Parser.parseAddSubExpression;

public class Main {
    public static void main(String[] args) {
        Optional<ParseResult<Expression>> exp;

        exp = parseAddSubExpression("-1", 0);
        System.out.println(exp.get().value().evaluate());
        // -1.0
        exp = parseAddSubExpression("--1", 0);
        System.out.println(exp.get().value().evaluate());
        // 1.0
        exp = parseAddSubExpression("+-+2", 0);
        System.out.println(exp.get().value().evaluate());
        // -2.0
        exp = parseAddSubExpression("-1 + 2", 0);
        System.out.println(exp.get().value().evaluate());
        // 1.0
        exp = parseAddSubExpression("--1 + 2", 0);
        System.out.println(exp.get().value().evaluate());
        // 3.0
        exp = parseAddSubExpression("(1 +2)", 0);
        System.out.println(exp.get().value().evaluate());
        // StackOverflowError
    }

}