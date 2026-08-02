package com.nokhrin.interpreter.minityped;

import com.nokhrin.interpreter.MiniTypedBaseListener;
import com.nokhrin.interpreter.MiniTypedParser;
import com.nokhrin.interpreter.MiniTypedParser.FuncParameterContext;
import com.nokhrin.interpreter.common.compiletime.FunctionSymbol;
import com.nokhrin.interpreter.common.compiletime.Parameter;
import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.compiletime.Symbol.Type;
import com.nokhrin.interpreter.common.compiletime.VariableSymbol;
import com.nokhrin.interpreter.common.runtime.GlobalScope;
import com.nokhrin.interpreter.common.runtime.LocalScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MiniTypedSemanticBuilder extends MiniTypedBaseListener {
    static final Logger LOGGER = LoggerFactory.getLogger(MiniTypedSemanticBuilder.class);

    private GlobalScope globalScope;
    private Scope currentScope;

    public Scope getGlobalScope() {
        return globalScope;
    }

    private Type resolveType(int tokenType) {
        return switch (tokenType) {
            case MiniTypedParser.INT_TYPE -> Type.INTEGER;
            case MiniTypedParser.FLOAT_TYPE -> Type.FLOAT;
            case MiniTypedParser.BOOL_TYPE -> Type.BOOLEAN;
            case MiniTypedParser.VOID_TYPE -> Type.VOID;
            default -> throw new IllegalArgumentException("Unknown type token: " + tokenType);
        };
    }

    public void enterProg(MiniTypedParser.ProgContext ctx) {
        globalScope = new GlobalScope();
        currentScope=globalScope;

    }

    public void enterFuncDefinition(MiniTypedParser.FuncDefinitionContext ctx){
        Type returnType = resolveType(ctx.funcSignature().type().start.getType());
        String funcName = ctx.funcSignature().ID().getText();

        List<Parameter> parameterList =new ArrayList<>();
        if (ctx.funcSignature().funcParameters() !=null){
            for (FuncParameterContext param : ctx.funcSignature().funcParameters().funcParameter()) {
                parameterList.add(new Parameter(param.ID().getText(), resolveType(param.type().start.getType())));
            }
            FunctionSymbol functionSymbol=new FunctionSymbol(funcName,parameterList, returnType, currentScope);
            currentScope.define(functionSymbol);
            LocalScope funcScope=new LocalScope(currentScope,funcName);
            for (Parameter parameter : parameterList){
                funcScope.define(new VariableSymbol(parameter.name(),parameter.type(), funcScope));
            }
            currentScope=funcScope;
        }
    }

    public void exitFuncDefinition(MiniTypedParser.FuncDefinitionContext ctx){
        currentScope=currentScope.getEnclosingScope();
    }

    public void enterBlock(MiniTypedParser.BlockContext ctx){
        currentScope=new LocalScope(currentScope,"block");
    }

    public void exitBlock(MiniTypedParser.BlockContext ctx){
        currentScope=currentScope.getEnclosingScope();
    }
}