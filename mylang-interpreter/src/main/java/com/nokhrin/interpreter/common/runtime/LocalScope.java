package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.compiletime.Scope;

public class LocalScope extends BaseScope{
    private final String name;

    public LocalScope(Scope enclosingScope, String name) {
        super(enclosingScope);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
