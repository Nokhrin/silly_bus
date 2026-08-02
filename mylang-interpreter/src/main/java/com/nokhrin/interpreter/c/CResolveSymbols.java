package com.nokhrin.interpreter.c;

import com.nokhrin.interpreter.CBaseListener;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.runtime.GlobalScope;
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
        String varName = ctx.ID().getSymbol().getText();
        Symbol var = currentScope.resolve(varName);
    }

    public void exitCall(CParser.CallContext ctx) {
        String funcName = ctx.ID().getSymbol().getText();
        Symbol func = currentScope.resolve(funcName);
    }
}
