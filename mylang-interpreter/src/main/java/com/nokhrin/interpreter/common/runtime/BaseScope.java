package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.compiletime.VariableSymbol;
import com.nokhrin.interpreter.common.values.EvalResult;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope {
    protected Scope enclosingScope;
    protected Map<String, Symbol> symbolTable = new LinkedHashMap<>();
    private final ValuesRegistry valuesRegistry=new ValuesRegistry();

    public BaseScope(Scope enclosingScope) {
        this.enclosingScope = enclosingScope;
    }

    public void setValue(VariableSymbol symbol, EvalResult value) {
        valuesRegistry.register(symbol, value);
    }

    public EvalResult getValue(VariableSymbol symbol) {
        try {
            return valuesRegistry.fetch(symbol);
        }catch (IllegalStateException e){
            if (enclosingScope instanceof BaseScope baseScope){
                return baseScope.getValue(symbol);
            }
            throw e;
        }
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
    public Symbol resolve(String symbolName) {
        Symbol symbol = symbolTable.get(symbolName);
        if (symbol != null) return symbol;
        if (enclosingScope != null) return enclosingScope.resolve(symbolName);
        throw new IllegalStateException("Symbol " + symbolName + " is not defined");
    }

    @Override
    public String toString() {
        return symbolTable.values().toString();
    }
}
