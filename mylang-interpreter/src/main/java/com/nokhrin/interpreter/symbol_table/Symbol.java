package com.nokhrin.interpreter.symbol_table;

public interface Symbol {
    enum Type {
        INT, FLOAT, VOID
    }
    String getName();
    Scope getScope();
    Type getType();
}
