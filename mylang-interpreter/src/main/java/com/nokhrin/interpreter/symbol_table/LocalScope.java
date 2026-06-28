package com.nokhrin.interpreter.symbol_table;

public class LocalScope extends BaseScope{
    private final String name;

    public LocalScope(Scope enclosingScope, String name) {
        super(enclosingScope);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
