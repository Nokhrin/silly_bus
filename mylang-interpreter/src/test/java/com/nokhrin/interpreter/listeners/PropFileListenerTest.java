package com.nokhrin.interpreter.listeners;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.nokhrin.interpreter.PropFileLexer;
import com.nokhrin.interpreter.PropFileParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PropFileListenerTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(PropFileListenerTest.class);

  private PropFileListener loader;
  private ParseTree tree;

  @BeforeMethod
  public void setLoader() throws IOException {
    loader = new PropFileListener();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties.txt");
    assertNotNull(inputStream);

    CharStream charStream = CharStreams.fromStream(inputStream);
    PropFileLexer lexer = new PropFileLexer(charStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    PropFileParser parser = new PropFileParser(tokens);
    tree = parser.file();
  }

  @Test
  public void loadPropertiesFromResourceFile_expectThreePropertiesLoaded() throws IOException {
    ParseTreeWalker walker = new ParseTreeWalker();
    PropFileListener loader = new PropFileListener();
    walker.walk(loader, tree);

    Map<String, String> actualProps = loader.props;

    LOGGER.info(actualProps.get("name"));
    assertEquals(actualProps.size(), 3);
    assertEquals(actualProps.get("name"), "\"John\"");
  }
}
