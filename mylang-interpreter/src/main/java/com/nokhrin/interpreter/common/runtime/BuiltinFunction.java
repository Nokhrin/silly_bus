package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.values.EvalResult;
import com.nokhrin.interpreter.common.values.ExprValue;

import java.util.List;

public interface BuiltinFunction {
    EvalResult apply(List<ExprValue> args);
}
