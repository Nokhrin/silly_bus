package com.nokhrin.expr;

import static org.testng.Assert.assertEquals;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ASTBuilderTest {

  private final Calculator calculator = new Calculator();
  private final ASTBuilder astBuilder = new ASTBuilder(new SymbolTable());

  private TerminalNode createOp(String op) {
    CalculatorLexer calculatorLexer = new CalculatorLexer(CharStreams.fromString(op));
    CommonTokenStream tokenStream = new CommonTokenStream(calculatorLexer);
    tokenStream.fill();
    return new TerminalNodeImpl(tokenStream.get(0));
  }

  // region toDouble
  @DataProvider(name = "toDoubleData")
  private Object[][] toDoubleData() {
    return new Object[][] {
      {new ExprValue.IntValue(5), 5.0},
      {new ExprValue.IntValue(-5), -5.0},
      {new ExprValue.DoubleValue(1.23), 1.23},
    };
  }

  @Test(groups = "unit", dataProvider = "toDoubleData")
  public void testToDouble(ExprValue input, double expected) {
    assertEquals(ASTBuilder.toDouble(input), expected);
  }

  // endregion toDouble

  // region applyBinOp
  @DataProvider(name = "applyBinOpData")
  private Object[][] applyBinOpData() {
    return new Object[][] {
      {new ExprValue.IntValue(2), new ExprValue.IntValue(3), "+", new ExprValue.IntValue(5)},
      {new ExprValue.IntValue(2), new ExprValue.IntValue(3), "-", new ExprValue.IntValue(-1)},
      {new ExprValue.IntValue(2), new ExprValue.IntValue(3), "*", new ExprValue.IntValue(6)},
      {new ExprValue.IntValue(2), new ExprValue.IntValue(2), "/", new ExprValue.IntValue(1)},
      {new ExprValue.IntValue(3), new ExprValue.IntValue(2), "/", new ExprValue.DoubleValue(1.5)},
      {
        new ExprValue.DoubleValue(3.5),
        new ExprValue.DoubleValue(2.5),
        "+",
        new ExprValue.DoubleValue(6.0)
      },
      {
        new ExprValue.DoubleValue(3),
        new ExprValue.DoubleValue(2.5),
        "-",
        new ExprValue.DoubleValue(0.5)
      },
    };
  }

  @Test(groups = "unit", dataProvider = "applyBinOpData")
  public void testApplyBinOp(ExprValue left, ExprValue right, String op, ExprValue expected) {
    TerminalNode operation = createOp(op);
    assertEquals(astBuilder.applyBinOp(left, right, operation), expected);
  }

  @Test(
      groups = "unit",
      expectedExceptions = ArithmeticException.class,
      expectedExceptionsMessageRegExp = "Деление на ноль")
  public void testApplyBinOpDivizionByZero() {
    astBuilder.applyBinOp(new ExprValue.IntValue(1), new ExprValue.IntValue(0), createOp("/"));
  }

  // endregion applyBinOp

}
