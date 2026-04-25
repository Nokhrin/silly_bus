package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * реализовать объекты в стиле Javascript
 * повторить пример использования
 * Тут разные методы и у них соответственно разные сигнатуры
 */
public class MethodSignatures {

    /**
     * Операции
     * Словарь: имя поля/метода -> значение поля/анонимная функция
     */
    static Map<String, Object> newMathOperations() {
        Map<String, Object> operations = new HashMap<>();

        // private int result;
        operations.put("result", 0);
        
        // Метод без аргументов
        //    public int getResult() {
        //        return result;
        //    }
        operations.put("getResult", (Supplier<Object>) () -> operations.get("result"));
        
        // Метод с одним аргументом
        //    public void setValue(int value) {
        //        this.result = value;
        //    }
        operations.put("setValue", (Consumer<Object>) value -> operations.put("result", value));
        
        // Метод с одним аргументом, использующий текущее значение
        //    public void multiplyBy(int multiplier) {
        //        this.result *= multiplier;
        //    }
        operations.put("multiplyBy", (Consumer<Object>) multiplier -> {
            Integer result = (Integer) operations.get("result");
            result *= (Integer) multiplier;
            operations.put("result", result);
        });
        
        // Метод с двумя аргументами
        //    public void add(int a, int b) {
        //        this.result = a + b;
        //    }
        operations.put("add", (BiConsumer<Integer, Integer>) (a, b) -> {
            Integer result = a + b;
            operations.put("result", result);
        });
        
        return operations;
    }

    /**
     * Обработчик выражения без аргументов
     */
    static Object calculatingOperation(Map<String, Object> object, String operation) {
        return calculatingOperation(object, operation, null, null);
    }

    /**
     * Обработчик выражения с 1 аргументом
     */
    static Object calculatingOperation(Map<String, Object> object, String operation, Object arg1) {
        return calculatingOperation(object, operation, arg1, null);
    }

    /**
     * Обработчик выражения с 2 аргументами
     *
     * @return
     */
    static Object calculatingOperation(Map<String, Object> object, String operation, Object arg1, Object arg2) {
        Object method = object.get(operation);
        
        if (arg1 == null && arg2 == null) {
            return ((Supplier<Object>) method).get();
        } else if (arg2 == null) {
            ((Consumer<Object>) method).accept(arg1);
            return null;
        } else {
            ((BiConsumer<Object, Object>) method).accept(arg1, arg2);
            return null;
        }
    }

    /**
     * Пример использования
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("=== начало ===");
        
        // MathOperations ops = new MathOperations();
        var ops = newMathOperations();
        
        // ops.add(5, 3);      // 2 аргумента → результат: 8
        calculatingOperation(ops, "add", 5, 3);
        
        // System.out.println(ops.getResult()); // 0 аргументов → вывод: 8
        System.out.println(calculatingOperation(ops, "getResult"));
        
        // ops.setValue(10);     // 1 аргумент → результат: 10
        calculatingOperation(ops, "setValue", 10);
        
        // System.out.println(ops.getResult()); // 0 аргументов → вывод: 10
        System.out.println(calculatingOperation(ops, "getResult"));
        
        // ops.multiplyBy(2);    // 1 аргумент → результат: 20
        calculatingOperation(ops, "multiplyBy", 2);
        
        // System.out.println(ops.getResult()); // 0 аргументов → вывод: 20
        System.out.println(calculatingOperation(ops, "getResult"));

        System.out.println("=== завершение ===");
    }
}

//=== начало ===
//8
//10
//20
//=== завершение ===
