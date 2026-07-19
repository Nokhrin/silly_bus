package com.nokhrin.interpreter.common;

import java.util.List;

public interface BuiltinFunction {
    EvalResult apply(List<ExprValue> args);
}
