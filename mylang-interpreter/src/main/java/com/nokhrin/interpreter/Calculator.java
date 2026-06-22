package com.nokhrin.interpreter;

import com.nokhrin.interpreter.symbol_table.GlobalScope;
import com.nokhrin.interpreter.visitors.CalcEvalVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calculator {
    private final GlobalScope globalScope;

    public Calculator() {
        this.globalScope = new GlobalScope();
    }

    public Calculator(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public ExprValue parse(String input) {
        CharStream charStream = CharStreams.fromString(input);
        CalcLexer lexer = new CalcLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalcParser parser = new CalcParser(tokens);
        ParseTree tree = parser.prog();

        CalcEvalVisitor visitor = new CalcEvalVisitor(globalScope);
        return visitor.visit(tree);
    }
}
