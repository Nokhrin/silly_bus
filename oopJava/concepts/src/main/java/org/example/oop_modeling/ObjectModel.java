package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;

/**
 * Моделирование объекта Animal в стиле JavaScript
 * структура = поля + методы
 * моделируется как Мар<String,Object>
 *     
 *     объект хранит функцию как правило
 *     v
 *     чтение по ключу возвращает правило (функцию)
 *     v
 *     применение параметра создает конкретный вызов функции
 *     v
 *     вызов выполняет функцию
 *     
 *     словарь Мар<String,Object> содержит ключ "makeSound" -> значение типа Object
 *     get("makeSound") -> значение типа Object => требуется приведение к Runnable
 *     значение типа Runnable - реализация функционального интерфейса с методом run()
 *     
 */
public class ObjectModel {
    /**
     * Эмуляция оператора new
     * возвращает Мар<String,Object>
     */
    static Map<String, Object> newAnimal() {
        Map<String, Object> animal = new HashMap<>();
        animal.put("name", "Class Animal");
        // явное приведение - гарантировать компилятору, что тип вычисленного значения будет Runnable
        animal.put("makeSound", (Runnable) () -> System.out.println("Some sound"));
        return animal;
    }

    /**
     * ПОлучает функцию из мапы, приводит к Runnable, выполняет
     * @param object
     * @param method
     * @return
     */
    static void callingAnimal(Map<String, Object> object, String method) {
        Object action = object.get(method);
        ((Runnable) action).run();
    }

    public static void main(String[] args) {
        System.out.println("=== начало ===");

        // Эмуляция оператора new
        var obj = newAnimal();
 
        // Вызвать у объекта метод makeSound()
        callingAnimal(obj, "makeSound");

        System.out.println("=== завершение ===");
    }
}

//=== начало ===
//Some sound
//=== завершение ===