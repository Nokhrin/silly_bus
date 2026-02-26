package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;

/**
 * Моделирование объекта Animal в стиле JavaScript
 * структура = поля + методы
 * моделируется как Мар<String,Object>
 */
public class ObjectModel {

    /**
     * Моделирование объекта Animal в стиле JavaScript
     * функциональный интерфейс
    // Объект Мар<String,Object> должен содержать значение makeSound, 
    // Тип значения Runnable
     */
    interface ISoundmaking {
        void make(String sound);
    }
    
    public static void main(String[] args) {
        System.out.println("=== начало ===");
        // class Animal
        Map<String, Object> Animal = new HashMap<>();
        ISoundmaking makeSound;

        makeSound = sound -> System.out.println("Animal: " + sound);
        Animal.put("makeSound", makeSound);

//    void makeSound() { System.out.println("Some sound"); }
        ((ISoundmaking)Animal.get("makeSound")).make("Some sound");

        // class Dog
        // переопределение
        Map<String, Object> Dog = new HashMap<>();
        makeSound = sound -> System.out.println("Dog: " + sound);
        Dog.put("makeSound", makeSound);

//    void makeSound() { System.out.println("Bark"); }
        ((ISoundmaking)Dog.get("makeSound")).make("Bark");

        System.out.println("=== завершение ===");
    }
}

//=== начало ===
//Animal: Some sound
//Dog: Bark
//=== завершение ===