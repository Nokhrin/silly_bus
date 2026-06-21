package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CLexer;
import com.nokhrin.interpreter.listeners.CCallGraph.FunctionListener;
import com.nokhrin.interpreter.listeners.CCallGraph.Graph;
import com.nokhrin.interpreter.CParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CCallGraphTest {

    @DataProvider(name = "srcToGraph")
    public Object[][] provideCases() {
        return new Object[][]{

//                {
//                        """
//            int main() { fact(5); a(); }
//            float fact(int n) {
//                print(n);
//                if (n == 0) then return 1;
//                return n * fact(n - 1);
//            }
//            void a() { b(); c(); }
//            void b() { c(); }
//            void c() { }
//            """,
//                        """
//            digraph G {
//              ranksep=.25;
//              edge [arrowsize=.5]
//              node [shape=circle, fontname="ArialNarrow",
//                    fontsize=12, fixedsize=true, height=.45];
//              main; fact; print; a; b; c;\s
//              main -> fact;
//              main -> a;
//              fact -> print;
//              fact -> fact;
//              a -> b;
//              a -> c;
//              b -> c;
//            }
//            """
//                },

//                {
//                        "",
//                        """
//            digraph G {
//              ranksep=.25;
//              edge [arrowsize=.5]
//              node [shape=circle, fontname="ArialNarrow",
//                    fontsize=12, fixedsize=true, height=.45];
//            }
//            """
//                },

                {
                        "void isolated() { }",
                        """
            digraph G {
              ranksep=.25;
              edge [arrowsize=.5]
              node [shape=circle, fontname="ArialNarrow",
                    fontsize=12, fixedsize=true, height=.45];
              isolated;\s
            }
            """
                },

                {
                        "int main() { print(42); }",
                        """
            digraph G {
              ranksep=.25;
              edge [arrowsize=.5]
              node [shape=circle, fontname="ArialNarrow",
                    fontsize=12, fixedsize=true, height=.45];
              main; print;\s
              main -> print;
            }
            """
                },

                {
                        """
            void ping() { pong(); }
            void pong() { ping(); }
            """,
                        """
            digraph G {
              ranksep=.25;
              edge [arrowsize=.5]
              node [shape=circle, fontname="ArialNarrow",
                    fontsize=12, fixedsize=true, height=.45];
              ping; pong;\s
              ping -> pong;
              pong -> ping;
            }
            """
                }
        };
    }

    @Test(dataProvider = "srcToGraph")
    public void testSrcToGraph(String srcInput, String expectedDOT) {
        Graph graph = convertSrcToGraph(srcInput);
        assertEquals(graph.toDOT(), expectedDOT);
    }

    private Graph convertSrcToGraph(String srcInput) {
        CharStream inputStream = CharStreams.fromString(srcInput);
        CLexer lexer = new CLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.prog();
        ParseTreeWalker walker = new ParseTreeWalker();
        FunctionListener listener = new FunctionListener();
        walker.walk(listener, tree);
        return listener.graph;
    }
}