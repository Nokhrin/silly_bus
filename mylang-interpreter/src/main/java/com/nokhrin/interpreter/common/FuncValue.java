package com.nokhrin.interpreter.common;

import com.nokhrin.interpreter.MiniScriptParser.BlockContext;
import com.nokhrin.interpreter.symbol_table.Scope;

import java.util.List;

public record FuncValue(
        String name,
        List<String> parameters,
        BlockContext body,
        Scope enclosingScope
) implements ExprValue {
    public String toString(){
        return String.format("<function %s>", name);
    }
}
