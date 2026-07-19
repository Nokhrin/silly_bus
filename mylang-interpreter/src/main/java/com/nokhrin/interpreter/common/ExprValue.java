package com.nokhrin.interpreter.common;

public sealed interface ExprValue extends EvalResult permits BoolValue, DoubleValue, IntValue {
}
