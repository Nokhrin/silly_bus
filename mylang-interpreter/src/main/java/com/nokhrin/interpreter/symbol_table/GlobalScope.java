package com.nokhrin.interpreter.symbol_table;

public class GlobalScope extends BaseScope{
    public GlobalScope(){
        super(null);
    }

    public String getName() {
        return "global";
    }
}
