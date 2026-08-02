package com.nokhrin.interpreter.common.compiletime;

import com.nokhrin.interpreter.common.values.EvalResult;

public interface FunctionBody {
    EvalResult invoke(Scope localScope);
}
