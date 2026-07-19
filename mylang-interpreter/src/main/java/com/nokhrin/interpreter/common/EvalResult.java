package com.nokhrin.interpreter.common;

public sealed interface EvalResult permits ExprValue, ControlSignal, VoidValue {
}
