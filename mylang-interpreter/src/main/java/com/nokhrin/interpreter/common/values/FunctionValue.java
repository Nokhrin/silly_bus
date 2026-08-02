package com.nokhrin.interpreter.common.values;

import com.nokhrin.interpreter.common.compiletime.FunctionBody;
import com.nokhrin.interpreter.common.compiletime.Parameter;
import com.nokhrin.interpreter.common.compiletime.Scope;

import java.util.List;

public record FunctionValue(
        String name,
        List<Parameter> parameters,
        FunctionBody body,
        Scope scope
) implements ExprValue {
}
