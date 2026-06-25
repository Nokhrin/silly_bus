package com.nokhrin.interpreter.symbol_table;

public interface Scope {
    String getName();
    Scope getEnclosingScope();
    void define(Symbol symbol);
    Symbol resolve(String name);

    default FunctionSymbol resolveFunction(String funcName){
        Symbol symbol=resolve(funcName);
        if (symbol instanceof FunctionSymbol funcSymbol) {
            return funcSymbol;
        }
        throw new IllegalArgumentException("no such function: " + funcName);
    }

    default VariableSymbol resolveSymbol (String varName){
        Symbol symbol=resolve(varName);
        if (symbol instanceof VariableSymbol varSymbol) {
            return varSymbol;
        }
        throw new IllegalArgumentException("no such variable: " + varName);
    }
}
