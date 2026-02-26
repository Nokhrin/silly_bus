package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;
import java.lang.Runnable;

/**
 * Моделирование объекта Animal в стиле JavaScript
 * структура = поля + методы
 * моделируется как Мар<String,Object>
 * <p>
 * объект хранит функцию как правило
 * v
 * чтение по ключу возвращает правило (функцию)
 * v
 * применение параметра создает конкретный вызов функции
 * v
 * вызов выполняет функцию
 * <p>
 * словарь Мар<String,Object> содержит ключ "makeSound" -> значение типа Object
 * get("makeSound") -> значение типа Object => требуется приведение к Runnable
 * значение типа Runnable - реализация функционального интерфейса с методом run()
 */
public class ObjectModel {
    /**
     * Эмуляция оператора new
     * возвращает Мар<String,Object>
     */
    static Map<String, Object> newAnimal() {
        Map<String, Object> animal = new HashMap<>();
        // явное приведение - гарантировать компилятору, что тип вычисленного значения будет Runnable
        animal.put("makeSound", (Runnable) () -> System.out.println("Some sound"));
        return animal;
    }

    /**
     * Добавить метод newDog()
     * result = {HashMap@822}  size = 1
     *  "makeSound" -> {ObjectModel$lambda@828}
     *   key = "makeSound"
     *   value = {ObjectModel$lambda@828}
     * @return
     */
    static Map<String, Object> newDog() {
        Map<String, Object> dog = new HashMap<>();
        
        // модель полиморфизма
        // - имя метода фиксировано: "makeSound"
        // - метод есть значение типа Runnable, получаемое по ключу "makeSound"
        // => эмуляция полиморфизма - вызов по фиксированному ключу метода, которые заранее неизвестен
        // => динамическая диспетчеризация - знаю как вызвать, что будет вызвано конкретно - не знаю
        //
        // модель vtable: 
        //  "makeSound" - id метода
        //  (Runnable) () -> System.out.println("Bark") - фактическая реализация метода, ссылку на которую содержит id метода
        // где
        //  () -> System.out.println("Bark") - реализация Runnable, объект Lambda
        //  (Runnable) - явное приведение, гарантия вернуть Runnable
        dog.put("makeSound", (Runnable) () -> System.out.println("Bark"));
        return dog;
    }

    /**
     * Получает функцию из мапы, приводит к Runnable, выполняет
     *
     * @param object
     * @return
     */
    static void callingAnimal(Map<String, Object> object) {
        // объяснить почему метод callingAnimal работает
        //
        // проявление полиморфизма 
        // - в compile-time объявляется тип Object
        // - в runtime - приведение Object к Runnable, вызов run()
        // - пара String->Object является корректным значением
        // - обработка экземпляра Object недоступна в compile - выполняется далее в runtime 
        //
        //  object содержит ключ makeSound, указывающий на экземпляр Object
        //  конкретно, makeSound указывает на экземпляр лямбда-выражения
        //
        //       object = {HashMap@817}  size = 1
        //       "makeSound" -> {ObjectModel$lambda@818} 
        //        key = "makeSound"
        //        value = {ObjectModel$lambda@818} 
        // 
        //  лямбда-выражение - анонимный экземпляр объекта типа Runnable
        //  => лямбда-выражение - реализует интерфейс с единственным методом (функциональный интерфейс)
        //  
        //  нет явного переопределения метода Runnable.run()
        //  гипотеза: выполняется неявное переопределение
        //      компилятор генерирует связь между run() и телом лямбда-выражения
        //
        //  каким образом вызов Runnable.run() приводит к вычислению лямбда-выражения?
        // 
        // определен целевой тип Runnable
        // (Runnable) () -> System.out.println("Bark")
        // v
        // тело лямбды компилятор преобразует в метод класса ObjectModel
        //  class ObjectModel {
        //      private static void lambda1() { System.out.println("Bark"); }
        //  }
        // v
        // вызов run() связывается с lambda1()
        // v
        // выполнение run() выполняет lambda1()
        // v
        // наблюдаемое поведение - вычислено лямбда-выражение
        // 
        // модель ошибки
        //
        // =========
        //
        //  ошибка возможна при нарушении структуры - нет ключа с таким именем
        //  => get вернет null
        //  => метод выбросит NPE
        // 
        //  dog.put("makeBadSound", (Runnable) () -> System.out.println("Bark"));
        //      v        
        //   object = {HashMap@821}  size = 1
        //   "makeBadSound" -> {ObjectModel$lambda@826} 
        //    key = "makeBadSound"
        //    value = {ObjectModel$lambda@826} 
        //      v        
        //  object.get("makeSound");
        //      v        
        //    null.run()
        //      v        
        //     NPE
        //
        //
        //  ошибка возможна при передаче типа, неприводимого к Runnable
        //  => get вернет значение, которое успешно инициализирует action, так как action принимает Object
        //  => приведение (Runnable) action выбросит исключение ClassCastException, 
        //  так как фактический тип action (в примере Integer) не является наследником Runnable 
        //
        //  dog.put("makeSound", 1);
        //      v
        //         object = {HashMap@826}  size = 1
        //         "makeSound" -> {Integer@830} 1
        //          key = "makeSound"
        //          value = {Integer@830} 1
        //
        //
        Object action = object.get("makeSound");
        System.out.println("DEBUG: " + action);
        ((Runnable) action).run();
    }

    public static void main(String[] args) {
        System.out.println("=== начало ===");

        // Эмуляция оператора new
        var obj = newAnimal();

        // Вызвать у объекта метод makeSound()
        callingAnimal(obj);

        // Не меняя реализацию callingAnimal передать объект dog, ожидаем ответ Bark
        obj = newDog();
        callingAnimal(obj);
        
        System.out.println("=== завершение ===");
    }
}

//=== начало ===
//Some sound
//Bark
//=== завершение ===