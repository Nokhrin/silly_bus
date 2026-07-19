package com.nokhrin.interpreter.common;

public sealed interface ControlSignal extends EvalResult permits BreakSignal, ContinueSignal {
}
