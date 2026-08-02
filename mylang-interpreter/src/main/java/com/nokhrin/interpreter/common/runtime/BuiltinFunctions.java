package com.nokhrin.interpreter.common.runtime;

import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.operations.ArithmeticOperations;
import com.nokhrin.interpreter.common.operations.TypeConversion;
import com.nokhrin.interpreter.common.values.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
                    case FunctionValue v -> String.valueOf(v);
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

    private static final Map<String, String> BUILTINS_HELP = Map.of(
            "print", "print(value) - output <value> to stdout\n Example: print(42)",
            "sin","sin(x) - sine of angle <x> in radians\n Example: sin(0) -> 0.0",
            "abs","abs(x) - absolute value of number <x>\n Example: abs(-1) -> 1",
            "pow","pow(base, exponent) - number <base> raised to <exponent>\n Example: pow(2, 3) -> 8"
    );

    public static String getFuncHelp(String funcName) {
        return BUILTINS_HELP.getOrDefault(funcName, "No help for: " + funcName);
    }

    public static String getGeneralHelp(){
        return "Built-in functions:\n" +
                BUILTINS_HELP.keySet().stream()
                        .map(key -> " " + key)
                        .collect(Collectors.joining("\n")) +
                "\n\nUse ?<name> for details. Example: ?print";
    }
}
