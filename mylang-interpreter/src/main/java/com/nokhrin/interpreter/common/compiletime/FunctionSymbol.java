package com.nokhrin.interpreter.common.compiletime;

import java.util.List;

public record FunctionSymbol(
        String name,
        List<Parameter> parameters,
        Type returnType,
        Scope scope
) implements Symbol {
    @Override
    public String getName() {
        return "";
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Scope getScope() {
        return null;
    }
}
