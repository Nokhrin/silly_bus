package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CLexer;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.symbol_table.FunctionSymbol;
import com.nokhrin.interpreter.symbol_table.Scope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CDefineSymbolsTest {

    private CDefineSymbols parseAndCollectSymbols(String sourceCode) {
        CharStream inputStream = CharStreams.fromString(sourceCode);
        CLexer lexer = new CLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.prog();

        CDefineSymbols listener = new CDefineSymbols();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        return listener;
    }

    @DataProvider(name = "functionDefinitionData")
    public Object[][] provideFunctionDefinitionCases() {
        return new Object[][]{
                {"int foo(int a, float b) { }", "foo", Symbol.Type.INT, new String[]{"a", "b"}, new Symbol.Type[]{Symbol.Type.INT, Symbol.Type.FLOAT}},
                {"void bar() { }", "bar", Symbol.Type.VOID, new String[]{}, new Symbol.Type[]{}},
                {"float baz(float x) { int y; }", "baz", Symbol.Type.FLOAT, new String[]{"x"}, new Symbol.Type[]{Symbol.Type.FLOAT}}
        };
    }

    @Test(dataProvider = "functionDefinitionData")
    public void functionDeclaration_parametersAndReturnType_correctSymbolTableStructure(
            String source,
            String functionName,
            Symbol.Type expectedReturnType,
            String[] paramNames,
            Symbol.Type[] paramTypes) {

        CDefineSymbols listener = parseAndCollectSymbols(source);
        FunctionSymbol functionSymbol = listener.globals.resolveFunction(functionName);
        assertNotNull(functionSymbol);
        assertEquals(functionSymbol.getType(), expectedReturnType);

        Scope functionScope = functionSymbol.getScope();
        for (int i = 0; i < paramNames.length; i++) {
            VariableSymbol paramSymbol = functionScope.resolveSymbol(paramNames[i]);
            assertNotNull(paramSymbol);
            assertEquals(paramSymbol.getType(), paramTypes[i]);
        }
    }
}