package com.nokhrin.interpreter.visitors;

import com.nokhrin.interpreter.CalcLexer;
import com.nokhrin.interpreter.CalcParser;
import com.nokhrin.interpreter.ExprValue;
import com.nokhrin.interpreter.IntValue;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CalcEvalVisitorTest {

    private ExprValue eval(String input) {
        GlobalScope globalScope = new GlobalScope();
      CalcLexer lexer = new CalcLexer(CharStreams.fromString(input));
      CommonTokenStream tokenStream = new CommonTokenStream(lexer);
      CalcParser parser = new CalcParser(tokenStream);
      ParseTree tree = parser.prog();
      CalcEvalVisitor visitor = new CalcEvalVisitor(globalScope);
      return visitor.visit(tree);
    }

    @DataProvider(name = "operations")
    public Object[][] createData() {
      return new Object[][] {
        {"1 + 2 * 3\n", new IntValue(7)},
        {"(1 + 2) * 3\n", new IntValue(9)},
        {"-5 + 10\n", new IntValue(5)},
        {"10 / 2\n", new IntValue(5)}
      };
    }

    @Test(dataProvider = "operations")
    public void testOperations(String input, ExprValue expected) {
      assertEquals(eval(input), expected);
    }

    @Test
    public void testVariables() {
      String program =
          """
                  x = 5
                  y = 10
                  x + y
                  """;
      assertEquals(eval(program), new IntValue(15));
    }
}
