package com.nokhrin.expr;

/** Атрибутивная грамматика. Выводит типы в runtime. */
public sealed interface ExprValue permits ExprValue.IntValue, ExprValue.DoubleValue {

  record IntValue(int value) implements ExprValue {
    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  record DoubleValue(double value) implements ExprValue {
    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }
}
