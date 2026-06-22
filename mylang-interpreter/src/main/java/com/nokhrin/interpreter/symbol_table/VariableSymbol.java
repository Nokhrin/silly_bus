package com.nokhrin.interpreter.symbol_table;

import com.nokhrin.interpreter.ExprValue;

public class VariableSymbol extends BaseSymbol{
    private final Symbol.Type returnType;
    private ExprValue value;

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

    public ExprValue getValue(){
        return value;
    }

    public void setValue(ExprValue value){
        this.value=value;
    }
}
