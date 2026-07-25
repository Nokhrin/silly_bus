package com.nokhrin.interpreter;

import com.nokhrin.interpreter.common.BuiltinFunctions;
import com.nokhrin.interpreter.common.EvalResult;
import com.nokhrin.interpreter.common.VoidValue;
import com.nokhrin.interpreter.miniscript.MiniScriptEvalVisitor;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import com.nokhrin.interpreter.symbol_table.Scope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Scanner;

public class Repl {
    private final Scope globalScope;
    private final MiniScriptEvalVisitor visitor;

    public Repl() {
        this.globalScope = new GlobalScope();
        this.visitor = new MiniScriptEvalVisitor(globalScope);
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
        return visitor.visit(tree);
    }


}
