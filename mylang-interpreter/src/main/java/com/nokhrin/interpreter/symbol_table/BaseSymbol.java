package com.nokhrin.interpreter.symbol_table;

public abstract class BaseSymbol implements Symbol {
    protected String name;
    protected Scope scope;

    public BaseSymbol(String name, Scope scope) {
        this.name = name;
        this.scope = scope;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Scope getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "<"
                + name
                + ":"
                + getClass().getSimpleName()
                + ">";
    }
}
