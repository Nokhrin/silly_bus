package com.nokhrin.interpreter.common;

import static com.nokhrin.interpreter.common.TypeConversion.toDouble;

public class ArithmeticOperations {

    private ArithmeticOperations() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ExprValue neg(ExprValue val) {
        if (val instanceof IntValue(long value)) {
            return new IntValue(-value);
        }
        if (val instanceof DoubleValue(double value)) {
            return new DoubleValue(-value);
        }
        throw new IllegalArgumentException("Cannot negate non-numeric: " + val);
    }

    public static ExprValue abs(ExprValue val) {
        if (val instanceof IntValue(long value)) {
            return new IntValue(Math.abs(value));
        }
        if (val instanceof DoubleValue(double value)) {
            return new DoubleValue(Math.abs(value));
        }
        throw new IllegalArgumentException("Cannot apply abs non-numeric: " + val);
    }

    public static ExprValue add(ExprValue left, ExprValue right) {
        if (left instanceof IntValue(long leftValue) && right instanceof IntValue(long rightValue)) {
            return new IntValue(leftValue + rightValue);
        }
        return new DoubleValue(toDouble(left) + toDouble(right));
    }

    public static ExprValue sub(ExprValue left, ExprValue right) {
        if (left instanceof IntValue(long leftValue) && right instanceof IntValue(long rightValue)) {
            return new IntValue(leftValue - rightValue);
        }
        return new DoubleValue(toDouble(left) - toDouble(right));
    }

    public static ExprValue mul(ExprValue left, ExprValue right) {
        if (left instanceof IntValue(long leftValue) && right instanceof IntValue(long rightValue)) {
            return new IntValue(leftValue * rightValue);
        }
        return new DoubleValue(toDouble(left) * toDouble(right));
    }

    public static ExprValue div(ExprValue left, ExprValue right) {
        double divisor = toDouble(right);
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        double result = toDouble(left) / divisor;
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return new IntValue((long) result);
        }
        return new DoubleValue(result);
    }

    public static ExprValue pow(ExprValue left, ExprValue right) {
        double result = Math.pow(toDouble(left), toDouble(right));
        if (left instanceof IntValue
                && right instanceof IntValue
                && result == Math.floor(result)
                && !Double.isInfinite(result)) {
            return new IntValue((long) result);
        }
        return new DoubleValue(result);
    }

}
