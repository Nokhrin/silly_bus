package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.SimpleCalcLexer;
import com.nokhrin.interpreter.SimpleCalcParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SimpleCalcListenerEvaluatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCalcListenerEvaluatorTest.class);

    @DataProvider(name = "calcData")
    public Object[][] provideExpressions() {
        return new Object[][]{
                {"1 + 2", 3},
                {"2 * 3", 6},
                {"1 + 2 * 3", 7},
                {"10 + 2 * 5 + 4", 24},
                {"42", 42},
                {"2 * 3 * 4", 24}
        };
    }

    @Test(dataProvider = "calcData")
    public void testExpressionEvaluation(String input, int expected) {
        CharStream charStream = CharStreams.fromString(input);
        SimpleCalcLexer lexer = new SimpleCalcLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleCalcParser parser = new SimpleCalcParser(tokens);
        ParseTree tree = parser.stat();
        LOGGER.info("Tree in text: {}", tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        SimpleCalcListenerEvaluator evaluator = new SimpleCalcListenerEvaluator();
        walker.walk(evaluator, tree);

        int actual = evaluator.getValue(tree);
        assertEquals(actual, expected);
    }
}