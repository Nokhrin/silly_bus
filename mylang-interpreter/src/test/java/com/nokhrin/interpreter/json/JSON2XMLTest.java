package com.nokhrin.interpreter.json;

import com.nokhrin.interpreter.JSONLexer;
import com.nokhrin.interpreter.JSONParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JSON2XMLTest {


    @DataProvider(name = "jsonToXmlCases")
    public Object[][] provideJsonToXmlCases() {
        return new Object[][]{
                {
                        "{\"description\" : \"An imaginary server config file\", \"logs\" : {\"level\":\"verbose\", \"dir\":\"/var/log\"}, \"host\" : \"antlr.org\", \"admin\": [\"parrt\", \"tombu\"], \"aliases\": []}",
                        "<description>An imaginary server config file</description>\n<logs><level>verbose</level>\n<dir>/var/log</dir>\n</logs>\n<host>antlr.org</host>\n<admin><element>parrt</element><element>tombu</element></admin>\n<aliases></aliases>\n"
                },
                {
                        "{}",
                        ""
                },
                {
                        "[]",
                        ""
                },
                {
                        "\"hello\"",
                        "hello"
                },
                {
                        "42",
                        "42"
                },
                {
                        "true",
                        "true"
                },
                {
                        "null",
                        "null"
                },
                {
                        "[{\"id\":1},{\"id\":2}]",
                        "<element><id>1</id>\n</element><element><id>2</id>\n</element>"
                },
                {
                        "[1, 2, 3]",
                        "<element>1</element><element>2</element><element>3</element>"
                },
                {
                        "[true, false, null]",
                        "<element>true</element><element>false</element><element>null</element>"
                },
                {
                        "[[1, 2], [3, 4]]",
                        "<element><element>1</element><element>2</element></element><element><element>3</element><element>4</element></element>"
                }
        };
    }

    @Test(dataProvider = "jsonToXmlCases")
    public void testJsonToXmlConversion_inputJson_expectedXml(String jsonInput, String expectedXml) {
        String actualXml = convertToJsonXml(jsonInput);
        assertEquals(actualXml, expectedXml);
    }

    private String convertToJsonXml(String jsonInput) {
        CharStream inputStream = CharStreams.fromString(jsonInput);
        JSONLexer lexer = new JSONLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        ParseTree tree = parser.json();
        ParseTreeWalker walker = new ParseTreeWalker();
        JSON2XML listener = new JSON2XML();
        walker.walk(listener, tree);
        return listener.getXML(tree);
    }
}