package com.nokhrin.interpreter.common.operations;

import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.values.*;

public class TypeConversion {

    private TypeConversion() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static long toLong(EvalResult val) {
        return (long) switch (val) {
            case IntValue intValue -> intValue.value();
            case DoubleValue doubleValue -> doubleValue.value();
            default -> throw new IllegalStateException("Unexpected value: " + val);
        };
    }

    public static double toDouble(EvalResult val) {
        return switch (val) {
            case IntValue intValue -> intValue.value();
            case DoubleValue doubleValue -> doubleValue.value();
            default -> throw new IllegalStateException("Unexpected value: " + val);
        };
    }

    public static EvalResult wrap(double val) {
        if (val == Math.floor(val) && !Double.isInfinite(val)) {
            return new IntValue((long) val);
        }
        return new DoubleValue(val);
    }

    public static Symbol.Type inferType(EvalResult value) {
        return switch (value) {
            case IntValue _ -> Symbol.Type.INTEGER;
            case DoubleValue _ -> Symbol.Type.FLOAT;
            case BoolValue _ -> Symbol.Type.BOOLEAN;
            case VoidValue _ -> Symbol.Type.VOID;
            case FunctionValue functionValue -> Symbol.Type.FUNCTION;
        };
    }

}
