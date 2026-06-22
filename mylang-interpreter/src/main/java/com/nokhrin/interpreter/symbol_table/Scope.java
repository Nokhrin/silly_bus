package com.nokhrin.interpreter.symbol_table;

public interface Scope {
    String getName();
    Scope getEnclosingScope();
    void define(Symbol symbol);
    Symbol resolve(String name);
}
