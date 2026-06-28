package com.nokhrin.interpreter.c;

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

import static org.testng.Assert.*;

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
                {
                        "int f() { i = 3; }",
                        "no such variable: i"
                },
                {
                        "int f() { z(); }",
                        "no such function: z"
                },
        };
    }

    @Test(dataProvider = "vars1ErrorCases")
    public void vars1File_invalidReferences_expectedIllegalArgumentException(String source, String expectedMessagePart) throws IOException {
        IllegalArgumentException exception = null;
        try {
            resolveSymbols(source);
        } catch (IllegalArgumentException e) {
            exception = e;
        }

        assertNotNull(exception, "Expected IllegalArgumentException was not thrown");
        assertTrue(
                exception.getMessage().contains(expectedMessagePart),
                "Expected message to contain '" + expectedMessagePart + "', but got: " + exception.getMessage()
        );
    }
}