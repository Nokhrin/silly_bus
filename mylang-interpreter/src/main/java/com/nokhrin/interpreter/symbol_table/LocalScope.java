package com.nokhrin.interpreter.symbol_table;

public class LocalScope extends BaseScope{
    private String name;

    public LocalScope(Scope parent, String name) {
        super(parent);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
