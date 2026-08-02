package com.nokhrin.interpreter.common.compiletime;

public record VariableSymbol(
        String name,
        Type type,
        Scope scope
) implements Symbol{

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Scope getScope() {
        return scope;
    }
}
