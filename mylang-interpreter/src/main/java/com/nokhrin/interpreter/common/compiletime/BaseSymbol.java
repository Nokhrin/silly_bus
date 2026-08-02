package com.nokhrin.interpreter.common.compiletime;

public abstract class BaseSymbol implements Symbol {
    protected String name;
    protected Scope scope;

    public BaseSymbol(String name, Scope scope) {
        this.name = name;
        this.scope = scope;
    }
}
