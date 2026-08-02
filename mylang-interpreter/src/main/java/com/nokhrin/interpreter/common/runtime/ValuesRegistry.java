package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.compiletime.VariableSymbol;
import com.nokhrin.interpreter.common.values.EvalResult;

import java.util.HashMap;
import java.util.Map;

public class ValuesRegistry {
    private final Map<VariableSymbol, EvalResult> values=new HashMap<>();

    public void register(VariableSymbol symbol, EvalResult value){
        values.put(symbol,value);
    }

    public EvalResult fetch(VariableSymbol symbol) {
        EvalResult value = values.get(symbol);
        if (value==null){
            throw new IllegalStateException("Variable " + symbol.name() + " not found");
        }
        return value;
    }
}
