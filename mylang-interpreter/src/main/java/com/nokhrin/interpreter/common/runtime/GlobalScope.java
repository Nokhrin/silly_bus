package com.nokhrin.interpreter.common.runtime;

public class GlobalScope extends BaseScope{
    public GlobalScope(){
        super(null);
    }

    public String getName() {
        return "global";
    }
}
