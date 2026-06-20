package com.nokhrin.interpreter;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SymbolTable {
  static final Logger LOGGER = LoggerFactory.getLogger(SymbolTable.class);
  private final Map<String, ExprValue> memory = new HashMap<>();

  public void insert(String name, ExprValue value) {
    if (memory.containsKey(name)) {
      LOGGER.info("Значение переменной {} обновлено", name);
    }
    memory.put(name, value);
  }

  public ExprValue lookup(String name) {
    if (!memory.containsKey(name)) {
      throw new IllegalArgumentException("Переменная " + name + " не определена");
    }
    return memory.get(name);
  }

  public void delete(String name) {
    if (!memory.containsKey(name)) {
      throw new IllegalArgumentException("Переменная " + name + " не определена");
    }
    memory.remove(name);
  }
}
