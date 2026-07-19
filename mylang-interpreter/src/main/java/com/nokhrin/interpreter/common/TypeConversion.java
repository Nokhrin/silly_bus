package com.nokhrin.interpreter.common;

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

}
