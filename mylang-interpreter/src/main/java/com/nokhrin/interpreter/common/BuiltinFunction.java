package com.nokhrin.interpreter.common;

import java.util.List;

public interface BuiltinFunction {
    ExprValue apply(List<ExprValue> args);
}
