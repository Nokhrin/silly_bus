package com.nokhrin.interpreter.symbol_table;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope{
    protected Scope parent;
    protected Map<String, Symbol> symbolMap = new LinkedHashMap<>();

    public BaseScope(Scope parent){
        this.parent = parent;
    }

    @Override
    public String getName() {
        return "base_scope";
    }

    @Override
    public Scope getEnclosingScope() {
        return parent;
    }

    @Override
    public void define(Symbol symbol) {
        symbolMap.put(symbol.getName(), symbol);
    }

    @Override
    public Symbol resolve(String name) {
        Symbol symbol = symbolMap.get(name);
        if (symbol != null) return symbol;
        if (parent != null) return parent.resolve(name);
        return null;
    }
}
