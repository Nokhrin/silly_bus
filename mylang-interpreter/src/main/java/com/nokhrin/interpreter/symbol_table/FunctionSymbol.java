package com.nokhrin.interpreter.symbol_table;

public class FunctionSymbol extends BaseSymbol{
    private final Symbol.Type returnType;
    public FunctionSymbol(String name, Symbol.Type returnType, Scope scope) {
        super(name, scope);
        this.returnType = returnType;
    }

    @Override
    public Type getType() {
        return returnType;
    }
}
