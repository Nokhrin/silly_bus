package com.nokhrin.expr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calculator {
  private SymbolTable symbolTable = new SymbolTable();

  public ExprValue parse(String input) {
    CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    CalculatorParser parser = new CalculatorParser(tokens);

    ParseTree parseTree = parser.program();

    System.out.println("ast: " + parseTree.toStringTree(parser));
    System.out.println("errors total: " + parser.getNumberOfSyntaxErrors());

    return new ASTBuilder(symbolTable).visit(parseTree);
  }
}
