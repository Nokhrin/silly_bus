package com.nokhrin.interpreter.common.compiletime;

public interface Symbol {
    String getName();
    Type getType();
    Scope getScope();

    enum Kind {
        VARIABLE, PARAMETER, FUNCTION, CLASS
    }

    enum Type {
        INTEGER, FLOAT, BOOLEAN, VOID, FUNCTION
    }
}
