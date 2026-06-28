package com.nokhrin.interpreter.miniscript;

import com.nokhrin.interpreter.MiniScriptLexer;
import com.nokhrin.interpreter.MiniScriptParser;
import com.nokhrin.interpreter.common.BoolValue;
import com.nokhrin.interpreter.common.DoubleValue;
import com.nokhrin.interpreter.common.ExprValue;
import com.nokhrin.interpreter.common.IntValue;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MiniScriptEvalVisitorTest {

    @DataProvider
    public Object[][] miniScriptEvalVisitor_evaluateActualEqualsExpected() {
        return new Object[][] {
//                {"1 + 2 - 3\n", new IntValue(0)},
//                {"2 + 3 * 4\n", new IntValue(14)},
//                {"(2 + 3) * 4\n", new IntValue(20)},
//                {"-5 + 3\n", new IntValue(-2)},
//                {"+10 - 5\n", new IntValue(5)},
//                {"3.14 + 2.86\n", new DoubleValue(6.0)},
                {"true\n", new BoolValue(true)},
                {"5 > 3\n", new BoolValue(true)},
                {"10 == 10\n", new BoolValue(true)},
                {"true AND false\n", new BoolValue(false)},
                {"NOT true\n", new BoolValue(false)}
        };
    }

    @Test(dataProvider = "miniScriptEvalVisitor_evaluateActualEqualsExpected")
    public void miniScriptEvalVisitor_evaluateActualEqualsExpected(String input, ExprValue expected) {
        var lexer = new MiniScriptLexer(CharStreams.fromString(input));
        var tokens = new CommonTokenStream(lexer);
        var parser = new MiniScriptParser(tokens);
        ParseTree tree = parser.prog();

        GlobalScope globals = new GlobalScope();
        var visitor = new MiniScriptEvalVisitor(globals);

        ExprValue result = visitor.visit(tree);
        assertEquals(result, expected);
    }
}