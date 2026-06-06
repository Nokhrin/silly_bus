package com.nokhrin.expr;

import java.util.HashMap;
import java.util.Map;

/** Хранит состояние переменных. */
public class SymbolTable {
  private final Map<String, ExprValue> vars = new HashMap<>();

  public void assign(String name, ExprValue value) {
    vars.put(name, value);
  }

  public ExprValue get(String name) {
    if (!vars.containsKey(name)) {
      throw new IllegalArgumentException("Переменная " + name + " не определена");
    }
    return vars.get(name);
  }
}
