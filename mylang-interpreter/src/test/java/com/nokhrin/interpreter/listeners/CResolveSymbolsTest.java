package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CLexer;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CResolveSymbolsTest {

    private String loadResourceContent(String resourceName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourceName);
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private void resolveSymbols(String sourceCode) throws IOException {
        CharStream inputStream = CharStreams.fromString(sourceCode);
        CLexer lexer = new CLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.prog();

        CDefineSymbols defineListener = new CDefineSymbols();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(defineListener, tree);

        GlobalScope globals = defineListener.globals;
        CResolveSymbols resolveListener = new CResolveSymbols(globals, defineListener.scopes);

        walker.walk(resolveListener, tree);
    }

    @Test
    public void vars2File_validScopingAndResolution_noExceptionsThrown() throws IOException {
        String source = loadResourceContent("vars2.c");
        resolveSymbols(source);
    }

    @DataProvider(name = "vars1ErrorCases")
    public Object[][] provideVars1ErrorCases() {
        return new Object[][]{
                {"int f(int x, float y) {\ng();\ni = 3;\ng = 4;\nreturn x + y;\n}\nvoid g() {\nint x = 0;\nfloat y;\ny = 9;\nf();\nz();\ny();\nx = f;\n}", "no such variable: i"},
                {"int f(int x, float y) {\ng();\ni = 3;\ng = 4;\nreturn x + y;\n}\nvoid g() {\nint x = 0;\nfloat y;\ny = 9;\nf();\nz();\ny();\nx = f;\n}", "g is not a variable"},
                {"int f(int x, float y) {\ng();\ni = 3;\ng = 4;\nreturn x + y;\n}\nvoid g() {\nint x = 0;\nfloat y;\ny = 9;\nf();\nz();\ny();\nx = f;\n}", "no such function: z"},
                {"int f(int x, float y) {\ng();\ni = 3;\ng = 4;\nreturn x + y;\n}\nvoid g() {\nint x = 0;\nfloat y;\ny = 9;\nf();\nz();\ny();\nx = f;\n}", "y is not a function"},
                {"int f(int x, float y) {\ng();\ni = 3;\ng = 4;\nreturn x + y;\n}\nvoid g() {\nint x = 0;\nfloat y;\ny = 9;\nf();\nz();\ny();\nx = f;\n}", "f is not a variable"}
        };
    }

    @Test(dataProvider = "vars1ErrorCases", expectedExceptions = IllegalArgumentException.class)
    public void vars1File_invalidReferences_expectedIllegalArgumentException(String source, String expectedMessagePart) throws IOException {
        try {
            resolveSymbols(source);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains(expectedMessagePart)) {
                throw new AssertionError("Expected exception message to contain '" + expectedMessagePart + "', but got: " + e.getMessage());
            }
            throw e;
        }
    }
}