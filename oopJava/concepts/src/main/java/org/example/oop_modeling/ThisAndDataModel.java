package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Моделирование this и данных
 * > wip
 * <p>
 * Задача смоделировать работу ООП в стиле Javascript в аспектах:
 * • Чтение данных объекта
 * • Изменение данных объекта
 * • Использование данных объекта с учетом типа
 */
public class ThisAndDataModel {

    /**
     * Вычисление с параметром
     *
     * @return
     */
    static Object calculatingOperation(Map<String, Object> object, String operation, Object value) {
        Object calculation = object.get(operation);
        if (value == null) {
            return ((Supplier<Object>) calculation).get();
        } else {
            ((Consumer<Object>) calculation).accept(value);
            return null;
        }
    }

    /**
     * Вычисление без параметра
     *
     * @return
     */
    static Object calculatingOperation(Map<String, Object> object, String operation) {
        return calculatingOperation(object, operation, null);
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
        calculator.put("setValue", (Consumer<Integer>) v -> calculator.put("value", v));

        // Метод, использующий значение поля в математических операциях
        //    public int calculateSquare() {
        //        return value * value;
        //    }
        // требуется передать значение
        // => параметризация лямбда-выражения => Runnable не подходит, Consumer не подходит, использую Supplier
        calculator.put("calculateSquare", (Supplier<Integer>) () -> {
            Integer value = (Integer) calculator.get("value");
            if (value == null) {
                throw new IllegalArgumentException("Калькулятору не передано число");
            }
            return value * value;
        });


        // Геттер для текущего значения (опционально)
        //    public int getValue() {
        //        return value;
        //    }
        calculator.put("getValue", (Supplier<Object>) () -> calculator.get("value"));

        return calculator;
    }

    public static void main(String[] args) {
        System.out.println("=== начало ===");

        // Создать экземпляр
        var calc = newCalculator();
        System.out.println(calculatingOperation(calc, "getValue"));
        // null

        // * • вычислить квадрат (вызов метода)  
        // * ◦ реализация должна прочитывать value  
//        System.out.println(calculatingOperation(calc, "calculateSquare"));
        // IllegalArgumentException: Калькулятору не передано число

        // * • Установить новое значение и вычислить квадрат  
        calculatingOperation(calc, "setValue", 8);
        System.out.println(calculatingOperation(calc, "getValue"));
        System.out.println(calculatingOperation(calc, "calculateSquare"));

        // * • Установить не допустимое значение (string или другой тип) и попытаться вычислить квадрат  
//        calculatingOperation(calc, "setValue", "восемь");
        // по спеке - тип значения value - int, в конструкторе привожу к Integer, передача строки приводит к исключению
        //ClassCastException: class java.lang.String cannot be cast to class java.lang.Integer
        
        System.out.println(calculatingOperation(calc, "getValue"));
        System.out.println(calculatingOperation(calc, "calculateSquare"));

        // * всё взаимодействие с объектом должно проходить через его методы  

        System.out.println("=== завершение ===");
    }
}

//=== начало ===
//null
//8
//64
//8
//64
//=== завершение ===