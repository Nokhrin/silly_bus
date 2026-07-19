package com.nokhrin.interpreter.symbol_table;

import com.nokhrin.interpreter.common.EvalResult;

public class VariableSymbol extends BaseSymbol{
    private final Symbol.Type returnType;
    private EvalResult value;

    public VariableSymbol(String name, Symbol.Type returnType, Scope scope) {
        super(name, scope);
        this.returnType = returnType;
    }

    @Override
    public Type getType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "<" + getName() + ":t" + returnType + ">";
    }

    public EvalResult getValue(){
        return value;
    }

    public void setValue(EvalResult value){
        this.value=value;
    }
}
