package com.nokhrin.interpreter.common;

public sealed class ControlSignal extends RuntimeException permits BreakSignal, ContinueSignal, ReturnSignal {
}
