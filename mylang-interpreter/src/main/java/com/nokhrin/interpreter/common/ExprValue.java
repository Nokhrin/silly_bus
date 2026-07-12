package com.nokhrin.interpreter.common;

public sealed interface ExprValue extends EvalResult permits BoolValue, BreakSignal, ContinueSignal, DoubleValue, IntValue {
}
