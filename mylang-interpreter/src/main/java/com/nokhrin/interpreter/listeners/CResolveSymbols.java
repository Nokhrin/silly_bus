package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CBaseListener;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.symbol_table.FunctionSymbol;
import com.nokhrin.interpreter.symbol_table.GlobalScope;
import com.nokhrin.interpreter.symbol_table.Scope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class CResolveSymbols extends CBaseListener {
    ParseTreeProperty<Scope> scopes;
    GlobalScope globals;
    Scope currentScope;

    public CResolveSymbols(GlobalScope globals, ParseTreeProperty<Scope> scopes){
        this.scopes=scopes;
        this.globals=globals;
    }

    public void enterProg(CParser.ProgContext ctx) {
        currentScope = globals;
    }

    public void enterFuncDecl(CParser.FuncDeclContext ctx) {
        currentScope=scopes.get(ctx);
    }
    public void exitFuncDecl(CParser.FuncDeclContext ctx) {
        currentScope=currentScope.getEnclosingScope();
    }

    public void enterBlock(CParser.BlockContext ctx) {
        currentScope=scopes.get(ctx);
    }
    public void exitBlock(CParser.BlockContext ctx) {
        currentScope=currentScope.getEnclosingScope();
    }

    public void exitVar(CParser.VarContext ctx) {
        String name = ctx.ID().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if (var == null) {
            throw new IllegalArgumentException("No such variable: "
                    + ctx.ID().getSymbol());
        }
        if (var instanceof FunctionSymbol) {
            throw new IllegalArgumentException("Not a variable: "
                    + ctx.ID().getSymbol());

        }
    }

    public void exitCall(CParser.CallContext ctx) {
        String name = ctx.ID().getSymbol().getText();
        Symbol var = currentScope.resolve(name);
        if (var == null) {
            throw new IllegalArgumentException("No such function: "
                    + ctx.ID().getSymbol());
        }
        if (var instanceof FunctionSymbol) {
            throw new IllegalArgumentException("Not a function: "
                    + ctx.ID().getSymbol());

        }
    }
}
