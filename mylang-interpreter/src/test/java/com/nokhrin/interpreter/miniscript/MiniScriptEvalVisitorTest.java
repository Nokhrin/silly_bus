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

    private ExprValue eval(String input) {
        var lexer = new MiniScriptLexer(CharStreams.fromString(input));
        var tokens = new CommonTokenStream(lexer);
        var parser = new MiniScriptParser(tokens);
        ParseTree tree = parser.prog();
        GlobalScope globals = new GlobalScope();
        var visitor = new MiniScriptEvalVisitor(globals);
        return visitor.visit(tree);
    }

    @DataProvider
    public Object[][] miniScriptEvalVisitor_evaluateArithmetic_actualEqualsExpected() {
        return new Object[][] {
                {"5 + 3\n", new IntValue(8)},
                {"10 - 2\n", new IntValue(8)},
                {"4 * 5\n", new IntValue(20)},
                {"20 / 4\n", new IntValue(5)},
                {"2 + 3 * 4\n", new IntValue(14)},
                {"(2 + 3) * 4\n", new IntValue(20)},
                {"-5 + 3\n", new IntValue(-2)},
                {"+10 - 5\n", new IntValue(5)},
                {"3.14 + 2.86\n", new DoubleValue(6.0)},
                {"1.234e-3 + 0.001\n", new DoubleValue(0.002234)},
                {".5 * 2\n", new DoubleValue(1.0)},
                {"1E2 + 50\n", new DoubleValue(150.0)},
                {"1e2 + 50\n", new DoubleValue(150.0)},
                {"1 + 2 - 3\n", new IntValue(0)},
                {"10 / 2 * 3\n", new IntValue(15)},
                {"10 - 2 - 3\n", new IntValue(5)},
                {"(1 + 2) * 3\n", new IntValue(9)},
        };
    }

    @Test(dataProvider = "miniScriptEvalVisitor_evaluateArithmetic_actualEqualsExpected")
    public void miniScriptEvalVisitor_evaluateArithmetic_actualEqualsExpected(String input, ExprValue expected) {
        assertEquals(eval(input), expected);
    }

    @DataProvider
    public Object[][] miniScriptEvalVisitor_evaluateLogic_actualEqualsExpected() {
        return new Object[][] {
                {"true AND false\n", new BoolValue(false)},
                {"true OR false\n", new BoolValue(true)},
                {"NOT true\n", new BoolValue(false)},
                {"NOT false\n", new BoolValue(true)},
                {"true AND true\n", new BoolValue(true)},
                {"false OR false\n", new BoolValue(false)},
                {"NOT (true AND false)\n", new BoolValue(true)},
                {"NOT (false OR false)\n", new BoolValue(true)},
                {"true AND true AND false\n", new BoolValue(false)},
                {"false OR false OR true\n", new BoolValue(true)},
        };
    }

    @Test(dataProvider = "miniScriptEvalVisitor_evaluateLogic_actualEqualsExpected")
    public void miniScriptEvalVisitor_evaluateLogic_actualEqualsExpected(String input, ExprValue expected) {
        assertEquals(eval(input), expected);
    }

    @DataProvider
    public Object[][] miniScriptEvalVisitor_evaluateComparison_actualEqualsExpected() {
        return new Object[][] {
                {"5 > 3\n", new BoolValue(true)},
                {"3 > 5\n", new BoolValue(false)},
                {"10 == 10\n", new BoolValue(true)},
                {"3 != 4\n", new BoolValue(true)},
                {"5 >= 5\n", new BoolValue(true)},
                {"2 <= 3\n", new BoolValue(true)},
                {"5 < 3\n", new BoolValue(false)},
                {"10 != 10\n", new BoolValue(false)},
                {"5 >= 6\n", new BoolValue(false)},
                {"3 <= 2\n", new BoolValue(false)},
                {"5.5 > 3.5\n", new BoolValue(true)},
                {"5 > 3.5\n", new BoolValue(true)},
                {"3.5 < 5\n", new BoolValue(true)},
        };
    }

    @Test(dataProvider = "miniScriptEvalVisitor_evaluateComparison_actualEqualsExpected")
    public void miniScriptEvalVisitor_evaluateComparison_actualEqualsExpected(String input, ExprValue expected) {
        assertEquals(eval(input), expected);
    }

    @DataProvider
    public Object[][] miniScriptEvalVisitor_evaluateAssignment_actualEqualsExpected() {
        return new Object[][] {
                {"x = 5\nx\n", new IntValue(5)},
                {"y = 3.14\ny\n", new DoubleValue(3.14)},
                {"z = true\nz\n", new BoolValue(true)},
                {"x = 5\ny = 3\nx + y\n", new IntValue(8)},
                {"x = 5\ny = 3.14\nx + y\n", new DoubleValue(8.14)},
                {"x = 10\ny = 2\nx / y\n", new IntValue(5)},
                {"x = 5\nx = 10\nx\n", new IntValue(10)},
                {"x = 2\ny = 3\nz = x + y\nz\n", new IntValue(5)},
                {"a = 5\nb = 3\na > b\n", new BoolValue(true)},
                {"x = 5\ny = 3\nx + y > 7\n", new BoolValue(true)},
        };
    }

    @Test(dataProvider = "miniScriptEvalVisitor_evaluateAssignment_actualEqualsExpected")
    public void miniScriptEvalVisitor_evaluateAssignment_actualEqualsExpected(String input, ExprValue expected) {
        assertEquals(eval(input), expected);
    }
}