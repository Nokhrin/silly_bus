package com.nokhrin.interpreter.common.compiletime;

public interface Scope {
    String getName();
    Scope getEnclosingScope();
    void define(Symbol symbol);
    Symbol resolve(String name);
}
