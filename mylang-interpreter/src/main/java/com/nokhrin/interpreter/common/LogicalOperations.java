package com.nokhrin.interpreter.common;

import java.util.function.BiFunction;

import static com.nokhrin.interpreter.common.TypeConversion.toDouble;

public class LogicalOperations {

    private LogicalOperations() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static EvalResult or(EvalResult left, EvalResult right) {
        if (left instanceof BoolValue(boolean leftValue)
                && right instanceof BoolValue(boolean rightValue)) {
            return new BoolValue(leftValue || rightValue);
        }
        throw new IllegalStateException("Cannot apply OR to left non-bool: " + right);
    }

    public static EvalResult and(EvalResult left, EvalResult right) {
        if (left instanceof BoolValue(boolean leftValue)
                && right instanceof BoolValue(boolean rightValue)) {
            return new BoolValue(leftValue && rightValue);
        }
        throw new IllegalStateException("Cannot apply AND to left non-bool: " + right);
    }

    public static EvalResult not(EvalResult operand) {
        if (operand instanceof BoolValue(boolean value)) {
            return new BoolValue(!value);
        }
        throw new IllegalStateException("Cannot apply NOT to non-bool: " + operand);
    }

    public static boolean equals(EvalResult left, EvalResult right) {
        if (left instanceof IntValue(long leftValue) && right instanceof IntValue(long rightValue)){
            return leftValue == rightValue;
        }
        if (left instanceof DoubleValue(double leftValue) && right instanceof DoubleValue(double rightValue)){
            return leftValue == rightValue;
        }
        if (left instanceof BoolValue(boolean leftValue) && right instanceof BoolValue(boolean rightValue)){
            return leftValue == rightValue;
        }
        if (left instanceof IntValue(long leftValue) && right instanceof DoubleValue(double rightValue)){
            return leftValue == rightValue;
        }
        if (left instanceof DoubleValue(double leftValue) && right instanceof IntValue(long rightValue)){
            return leftValue == rightValue;
        }
        return false;
    }

    public static EvalResult compare(EvalResult left, String op, EvalResult right) {
        boolean result = switch (op) {
            case "==" -> equals(left, right);
            case "!=" -> !equals(left, right);
            case ">" -> compareNumeric(left, right, (l, r) -> l > r);
            case "<" -> compareNumeric(left, right, (l, r) -> l < r);
            case ">=" -> compareNumeric(left, right, (l, r) -> l >= r);
            case "<=" -> compareNumeric(left, right, (l, r) -> l <= r);
            default -> throw new IllegalStateException("Unexpected operator: " + op);
        };
        return new BoolValue(result);
    }

    public static boolean compareNumeric(
            EvalResult left, EvalResult right,
            BiFunction<Double, Double, Boolean> comparator) {
        return comparator.apply(toDouble(left), toDouble(right));
    }

}
