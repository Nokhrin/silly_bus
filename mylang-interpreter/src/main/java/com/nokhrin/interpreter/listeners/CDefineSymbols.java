package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CBaseListener;
import com.nokhrin.interpreter.CParser;
import com.nokhrin.interpreter.symbol_table.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
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
        LOGGER.debug("Globals: {}", globals);
    }

    public void enterFuncDecl(CParser.FuncDeclContext ctx) {
        String funcName = ctx.ID().getText();
        int tokenType = ctx.type().start.getType();
        Symbol.Type funcRetType = resolveType(tokenType);

        LocalScope funcScope = new LocalScope(currentScope, funcName);
        FunctionSymbol function = new FunctionSymbol(funcName, funcRetType, funcScope);
        currentScope.define(function);
        LOGGER.debug("Added function {} to it's local scope {}", funcName, funcScope.getName());

        scopes.put(ctx, funcScope);
        LOGGER.debug("Created LOCAL scope {}, function {}", funcScope.getName(), funcName);

        currentScope=funcScope;
        LOGGER.debug("Switched current scope to {}", currentScope.getName());
    }
    public void exitFuncDecl(CParser.FuncDeclContext ctx) {
        LOGGER.debug("Before exit Function {}, scope: {}", ctx.ID().getText(), currentScope.getName());
        currentScope=currentScope.getEnclosingScope();
        LOGGER.debug("After Exit Function {}, scope: {}", ctx.ID().getText(), currentScope.getName());
    }

    public void enterParam(CParser.ParamContext ctx) {
        LOGGER.debug("Enter func param {}", ctx.ID().getSymbol());
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    public void enterVarDecl(CParser.VarDeclContext ctx) {
        defineVar(ctx.type(), ctx.ID().getSymbol());
    }

    public void enterBlock(CParser.BlockContext ctx) {
        String scopeName;
        ParseTree parent = ctx.getParent();

        scopeName = switch (parent) {
            case CParser.FuncDeclContext funcDeclContext -> funcDeclContext.ID().getText();
            case CParser.IfStatContext ifStatContext -> "block-if";
            case CParser.WhileStatContext whileStatContext -> "block-while";
            case CParser.ForStatContext forStatContext -> "block-for";
            case null, default -> "block-nested";
        };

        LocalScope blockScope = new LocalScope(currentScope, scopeName);

        LOGGER.debug("Created {} scope: {}", scopeName, blockScope.getName());
        scopes.put(ctx, blockScope);
        currentScope = blockScope;
    }

    public void exitBlock(CParser.BlockContext ctx) {
        currentScope=currentScope.getEnclosingScope();
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
