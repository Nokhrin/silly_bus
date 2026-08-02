package com.nokhrin.interpreter.common.values;

public sealed class ControlSignal extends RuntimeException permits BreakSignal, ContinueSignal, ReturnSignal {
}
