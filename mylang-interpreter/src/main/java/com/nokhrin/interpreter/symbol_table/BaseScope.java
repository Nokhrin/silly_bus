package com.nokhrin.interpreter.symbol_table;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope{
    protected Scope enclosingScope;
    protected Map<String, Symbol> symbolTable = new LinkedHashMap<>();

    public BaseScope(Scope enclosingScope){
        this.enclosingScope = enclosingScope;
    }

    @Override
    public String getName() {
        return "base_scope";
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol symbol) {
        symbolTable.put(symbol.getName(), symbol);
    }

    @Override
    public Symbol resolve(String scopeName) {
        Symbol symbol = symbolTable.get(scopeName);
        if (symbol != null) return symbol;
        if (enclosingScope != null) return enclosingScope.resolve(scopeName);
        return null;
    }

    @Override
    public String toString() {
        return symbolTable.values().toString();
    }
}
