package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Моделирование this и данных  
 * > wip
 *
 * Задача смоделировать работу ООП в стиле Javascript в аспектах:  
 * • Чтение данных объекта  
 * • Изменение данных объекта  
 * • Использование данных объекта с учетом типа  
 */
public class ThisAndDataModel {

    /**
     * Вычисление
     * @return
     */
    static void calculatingOperation(Map<String, Object> object, String operation, Object value) {
        Object calculation = object.get(operation);
        if (calculation instanceof Consumer<?>) {
            ((Consumer<Object>) calculation).accept(value);
        } else if (calculation instanceof Supplier<?>) {
            ((Supplier<?>) calculation).get();
        } else if (calculation instanceof Runnable) {
            ((Runnable) calculation).run();
        } 
        
    }
    
    // * • Создать конструктор возвращающий данный объект тип javascript Мар <String,Object>, 
    static Map<String, Object> newCalculator() {
        
        // * с инициализированным значением и таблицей Vtable  
        Map<String, Object> calculator = new HashMap<>(); // представляет vtable

        // Поле класса
        // private int value; 
        calculator.put("value", null);

        // Метод, изменяющий значение поля
        //    public void setValue(int value) {
        //        this.value = value;
        //    }
        
        // требуется принять значение
        // => параметризация лямбда-выражения => Runnable не подходит, использую Consumer
        calculator.put("setValue", (Consumer<Object>) v -> calculator.put("value", v));
        
        // Метод, использующий значение поля в математических операциях
        //    public int calculateSquare() {
        //        return value * value;
        //    }

        // требуется передать значение
        // => параметризация лямбда-выражения => Runnable не подходит, Consumer не подходит, использую Supplier
        calculator.put("calculateSquare", (Supplier<Object>) () -> (Integer) calculator.get("value") * (Integer) calculator.get("value"));

        // Геттер для текущего значения (опционально)
        //    public int getValue() {
        //        return value;
        //    }
        calculator.put("getValue", (Runnable) () -> calculator.get("value"));
        
        return calculator;
    }

    public static void main(String[] args) {
        System.out.println("=== начало ===");

        // Создать экземпляр
        var calc = newCalculator();
//        calculatingOperation(calc, "getValue");
        
        // * • вычислить квадрат (вызов метода)  
//        calculatingOperation(calc, "calculateSquare");

        // * ◦ реализация должна прочитывать value  
        // ?
        
        // * • Установить новое значение и вычислить квадрат  
        calculatingOperation(calc, "setValue", 8);
//        calculatingOperation(calc, "getValue");
//        calculatingOperation(calc, "calculateSquare");
        
        // * • Установить не допустимое значение (string или другой тип) и попытаться вычислить квадрат  
        calculatingOperation(calc, "setValue", "восемь");
//        calculatingOperation(calc, "getValue");
//        calculatingOperation(calc, "calculateSquare");
        
        // * всё взаимодействие с объектом должно проходить через его методы  

        System.out.println("=== завершение ===");
    }
}
