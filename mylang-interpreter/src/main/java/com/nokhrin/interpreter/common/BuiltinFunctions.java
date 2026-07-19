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
                String output = switch (value) {
                    case IntValue(long v) -> String.valueOf(v);
                    case DoubleValue(double v) -> String.valueOf(v);
                    case BoolValue(boolean v) -> String.valueOf(v);
                };
                System.out.println(output);
            }

            return new VoidValue();
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
            EvalResult base = args.getFirst();
            EvalResult exponent = args.get(1);

            return ArithmeticOperations.pow(base, exponent);
        });

    }
}
