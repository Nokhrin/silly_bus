package com.nokhrin.interpreter.common.values;

public sealed interface ExprValue extends EvalResult permits BoolValue, DoubleValue, FunctionValue, IntValue {
}
