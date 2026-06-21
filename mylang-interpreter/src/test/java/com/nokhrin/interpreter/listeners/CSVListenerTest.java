package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CSVLexer;
import com.nokhrin.interpreter.CSVParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.*;

public class CSVListenerTest {

    @Test
    public void testParseFile() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input.csv");
        assertNotNull(inputStream);

        CharStream charStream = CharStreams.fromStream(inputStream);
        CSVLexer lexer = new CSVLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.file();

        ParseTreeWalker walker= new ParseTreeWalker();
        CSVListener listener = new CSVListener();
        walker.walk(listener, tree);

        assertEquals(listener.rows.toString(), "[{Details=Mid Bonus, Month=June, Amount=\"$2,000\"}, {Details=, Month=January, Amount=\"\"\"zippo\"\"\"}, {Details=Total Bonuses, Month=\"\", Amount=\"$5,000\"}]");
    }

}