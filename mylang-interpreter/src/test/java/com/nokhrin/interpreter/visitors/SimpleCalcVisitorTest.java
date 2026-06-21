package com.nokhrin.interpreter.visitors;

import com.nokhrin.interpreter.SimpleCalcLexer;
import com.nokhrin.interpreter.SimpleCalcParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SimpleCalcVisitorTest {
    @DataProvider(name = "calcData")
    public Object[][] provideExpressions() {
        return new Object[][]{
                {"simple addition", "1 + 2", 3},
                {"simple multiplication", "2 * 3", 6},
                {"precedence mul over add", "1 + 2 * 3", 7},
                {"complex expression", "10 + 2 * 5 + 4", 24},
                {"single number", "42", 42},
                {"multiplication chain", "2 * 3 * 4", 24}
        };
    }

    @Test(dataProvider = "calcData")
    public void testExpressionEvaluation(String description, String input, int expected) {
        CharStream charStream = CharStreams.fromString(input);
        SimpleCalcLexer lexer = new SimpleCalcLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleCalcParser parser = new SimpleCalcParser(tokens);

        SimpleCalcVisitor visitor = new SimpleCalcVisitor();
        int result = visitor.visit(parser.stat());

        assertEquals(result, expected, "Failed for: " + description + " with input: " + input);
    }
}