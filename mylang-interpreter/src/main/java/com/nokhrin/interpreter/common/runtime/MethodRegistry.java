package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.compiletime.FunctionBody;

import java.util.HashMap;
import java.util.Map;

public class MethodRegistry {
    private final Map<String, FunctionBody> methods = new HashMap<>();

    public void register(String name, FunctionBody body) {
        if (methods.containsKey(name)) {
            throw new IllegalArgumentException("Already registered Method: " + name);
        }
        methods.put(name, body);
    }

    public FunctionBody fetch(String name) {
        FunctionBody body = methods.get(name);
        if (body == null) {
            throw new IllegalArgumentException("Undefined method: " + name);
        }
        return body;
    }

}
