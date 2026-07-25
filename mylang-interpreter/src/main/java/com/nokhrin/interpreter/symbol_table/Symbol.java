package com.nokhrin.interpreter.symbol_table;

import com.nokhrin.interpreter.common.*;

public interface Symbol {
    enum Type {
        INT, FLOAT, BOOLEAN, VOID, FUNCTION
    }
    String getName();
    Scope getScope();
    Type getType();

    static Symbol.Type inferType(ExprValue value){
        return switch (value){
            case IntValue _ -> Type.INT;
            case DoubleValue _ -> Type.FLOAT;
            case BoolValue _ -> Type.BOOLEAN;
            case FuncValue _ -> Type.FUNCTION;
        };
    }
}
