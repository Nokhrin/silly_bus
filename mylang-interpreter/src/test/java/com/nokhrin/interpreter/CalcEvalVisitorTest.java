package com.nokhrin.interpreter;

import static org.testng.Assert.assertEquals;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CalcEvalVisitorTest {

  private double eval(String input) {
    CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    CalcParser parser = new CalcParser(tokenStream);
    ParseTree tree = parser.prog();
    CalcEvalVisitor visitor = new CalcEvalVisitor();
    return visitor.visit(tree);
  }

  @DataProvider(name = "operations")
  public Object[][] createData() {
    return new Object[][] {
      {"1 + 2 * 3\n", 7.0},
      {"(1 + 2) * 3\n", 9.0},
      {"-5 + 10\n", 5.0},
      {"10 / 2\n", 5.0}
    };
  }

  @Test(dataProvider = "operations")
  public void testOperations(String input, double expected) {
    assertEquals(eval(input), expected, 0.0001);
  }

  @Test
  public void testVariables() {
    String program =
        """
                x = 5
                y = 10
                x + y
                """;
    assertEquals(eval(program), 15.0);
  }
}
