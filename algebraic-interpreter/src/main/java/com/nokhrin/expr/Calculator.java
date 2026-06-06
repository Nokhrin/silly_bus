package com.nokhrin.expr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Calculator{

    public int parse(String input) {
        CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalculatorParser parser = new CalculatorParser(tokens);
        return new ASTBuilder().visit(parser.expression());
    }
}
