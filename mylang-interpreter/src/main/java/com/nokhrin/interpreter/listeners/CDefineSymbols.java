package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CBaseListener;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.symbol_table.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDefineSymbols extends CBaseListener {
    static final Logger LOGGER = LoggerFactory.getLogger(CDefineSymbols.class);
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    GlobalScope globals;
    Scope currentScope;

    private Symbol.Type resolveType(int tokenType){
        return switch (tokenType) {
            case CParser.INT_TYPE -> Symbol.Type.INT;
            case CParser.FLOAT_TYPE -> Symbol.Type.FLOAT;
            case CParser.VOID_TYPE -> Symbol.Type.VOID;
            default -> throw new IllegalArgumentException("Unknown type token: " + tokenType);
        };
    }

    public void enterProg(CParser.ProgContext ctx){
        globals=new GlobalScope();
        currentScope = globals;
    }
    public void exitProg(CParser.ProgContext ctx) {
        LOGGER.info("Globals: {}", globals);
    }

    public void enterFuncDecl(CParser.FuncDeclContext ctx) {
        String name = ctx.ID().getText();
        int tokenType = ctx.type().start.getType();
        Symbol.Type type = resolveType(tokenType);

        LocalScope funcScope = new LocalScope(currentScope, name);

        FunctionSymbol function = new FunctionSymbol(name, type, funcScope);
        currentScope.define(function);
        currentScope = funcScope;
        scopes.put(ctx, currentScope);
    }
    public void exitFuncDecl(CParser.FuncDeclContext ctx) {
        currentScope=currentScope.getEnclosingScope();
    }

    public void enterParam(CParser.ParamContext ctx) {
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    public void enterVarDecl(CParser.VarDeclContext ctx) {
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    void defineVar(CParser.TypeContext typeContext, Token nameToken) {
        int tokenType = typeContext.start.getType();
        Symbol.Type type = resolveType(tokenType);
        VariableSymbol var = new VariableSymbol(nameToken.getText(), type, currentScope);
        currentScope.define(var);

        LOGGER.debug("Defined variable: {} of type {} in scope {}",
                nameToken.getText(), type, currentScope.getName());
    }
}
