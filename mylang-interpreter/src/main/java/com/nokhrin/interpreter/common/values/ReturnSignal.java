package com.nokhrin.interpreter.common.values;

public final class ReturnSignal extends ControlSignal {
    public final EvalResult value;
    public ReturnSignal(EvalResult value) {
        this.value = value;
    }
}
