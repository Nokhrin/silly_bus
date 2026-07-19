package com.nokhrin.interpreter.common;

import java.util.HashMap;
import java.util.Map;

public class BuiltinFunctions {
    private static final Map<String,BuiltinFunction> REGISTRY = new HashMap<>();

    public static boolean isBuiltin(String funcName){
        return REGISTRY.containsKey(funcName);
    }

    public static BuiltinFunction get(String funcName) {
        return REGISTRY.get(funcName);
    }

    static {
        REGISTRY.put("print", args -> {
            for (ExprValue value : args){
                System.out.println(value); //TODO [nohal][2026-07-19 16:08:54]: expressions?
            }
            return null;
        });

        REGISTRY.put("sin", args -> {
            if (args.size() != 1 ) {
                throw new IllegalArgumentException("sin expected 1 argument, got " + args.size());
            }
            return new DoubleValue(Math.sin(TypeConversion.toDouble(args.getFirst())));
        });

        REGISTRY.put("abs", args -> {
            if (args.size() != 1 ) {
                throw new IllegalArgumentException("abs expected 1 argument, got " + args.size());
            }
            return ArithmeticOperations.abs(args.getFirst());
        });

        REGISTRY.put("pow", args -> {
            if (args.size() != 2 ) {
                throw new IllegalArgumentException("pow expected 2 arguments, got " + args.size());
            }
            return ArithmeticOperations.pow(args.getFirst(), new IntValue(Long.parseLong(args.get(1).toString())));
        });

    }
}
