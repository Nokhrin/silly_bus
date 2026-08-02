package com.nokhrin.interpreter;

import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.runtime.BuiltinFunctions;
import com.nokhrin.interpreter.common.runtime.MethodRegistry;
import com.nokhrin.interpreter.common.values.EvalResult;
import com.nokhrin.interpreter.common.values.VoidValue;
import com.nokhrin.interpreter.miniscript.MiniScriptEvalVisitor;
import com.nokhrin.interpreter.miniscript.MiniScriptSemanticBuilder;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Scanner;

public class ReplMiniScript {
    private final MethodRegistry methodRegistry;
    private final MiniScriptSemanticBuilder semanticBuilder;

    public ReplMiniScript(Scope currentScope, MethodRegistry methodRegistry, MiniScriptSemanticBuilder semanticBuilder) {
        this.semanticBuilder = semanticBuilder;
        this.methodRegistry = methodRegistry;
    }


    public void runSource(String sourceText){
        try {
            EvalResult result=eval(sourceText);
            if (!(result instanceof VoidValue)){
                System.out.println("-> " + result);
            }
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MiniScript REPL\n\\q -> exit, \\h -> help");
        System.out.print("> ");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;
            if (input.equals("\\q")) break;
            if (input.equals("\\h")) {
                System.out.println(BuiltinFunctions.getGeneralHelp());
                System.out.print("> ");
                continue;
            }

            try {
                EvalResult result = eval(input);
                if (!(result instanceof VoidValue)) {
                    System.out.println("-> " + result);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e);
            }

            System.out.print("> ");
        }
    }

    private EvalResult eval(String input) {
        var lexer = new MiniScriptLexer(CharStreams.fromString(input));
        var tokens = new CommonTokenStream(lexer);
        var parser = new MiniScriptParser(tokens);
        ParseTree tree = parser.prog();

        MiniScriptSemanticBuilder semanticBuilder=new MiniScriptSemanticBuilder();
        ParseTreeWalker walker=new ParseTreeWalker();
        walker.walk(semanticBuilder, tree);
        Scope globalScope = semanticBuilder.getGlobalScope();
        var visitor = new MiniScriptEvalVisitor(globalScope, methodRegistry);
        return visitor.visit(tree);
    }
}
