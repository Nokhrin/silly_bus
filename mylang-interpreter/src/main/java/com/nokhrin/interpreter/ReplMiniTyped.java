package com.nokhrin.interpreter;

import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.runtime.BaseScope;
import com.nokhrin.interpreter.common.runtime.BuiltinFunctions;
import com.nokhrin.interpreter.common.runtime.MethodRegistry;
import com.nokhrin.interpreter.common.values.EvalResult;
import com.nokhrin.interpreter.common.values.VoidValue;
import com.nokhrin.interpreter.common.runtime.GlobalScope;
import com.nokhrin.interpreter.minityped.MiniTypedEvalVisitor;
import com.nokhrin.interpreter.minityped.MiniTypedSemanticBuilder;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Scanner;

public class ReplMiniTyped {
    private final MethodRegistry methodRegistry;
    private final MiniTypedSemanticBuilder semanticBuilder;

    public ReplMiniTyped(Scope currentScope, MethodRegistry methodRegistry, MiniTypedSemanticBuilder semanticBuilder) {
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
        System.out.println("MiniTyped REPL\n\\q -> exit, \\h -> help");
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

            if (input.startsWith("?")){
                provideHelp(input);
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
        var lexer = new MiniTypedLexer(CharStreams.fromString(input));
        var tokens = new CommonTokenStream(lexer);
        var parser = new MiniTypedParser(tokens);
        ParseTree tree = parser.prog();

        ParseTreeWalker walker=new ParseTreeWalker();
        walker.walk(semanticBuilder, tree);

        var visitor = new MiniTypedEvalVisitor((BaseScope) semanticBuilder.getGlobalScope(), methodRegistry);
        return visitor.visit(tree);
    }

    private void provideHelp(String input){
        String command = input.substring(1).trim();
        if (command.isEmpty()){
            System.out.println(BuiltinFunctions.getGeneralHelp());
        } else {
            if (BuiltinFunctions.isBuiltin(command)){
                System.out.println(BuiltinFunctions.getFuncHelp(command));
            }else{
                System.err.println("Unknown function: " + command);
            }
        }
    }

}
