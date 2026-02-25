package org.example.oop_modeling;

/**
 * Лямбда-функции - базовые операции
 */
public class LambdasBasics {
    /**
     * работы с лямбдами как с переменными
     */
    public static void main(String[] args) {
        System.out.println("=== начало ===");
        
        // а) определить/объявить переменную типа лямбды
        IIntegerAction lambdaN; // объявление переменной
        System.out.println("\nА");
        System.out.println("объявление переменной типа IIntegerAction: lambdaN");
//        System.out.println("тип переменной lambdaN: " + lambdaN.getClass().getSimpleName());
        //java: variable lambdaN might not have been initialized => переменная объявлена, не инициализирована
        
        // б) запись в переменную значения типа лямбды
        lambdaN = n -> n*n;
        System.out.println("\nБ");
        System.out.println("тип в runtime - ссылка на объект лямбды: " + lambdaN.getClass().getSimpleName());
        //тип переменной lambdaN: LambdasBasics$$Lambda/0x000076c6f4002bf8
        
        // в) чтение значения типа лямбды из переменной - с целью выполнить метод
        System.out.println("\nВ");
        System.out.println("результат вычисления: " + lambdaN.perform(5)); // вычисление
        //результат вычисления: 25

        // г) передача значения типа лямбды из переменной в метод через стек 
        //     * -> передача указателя на функцию методу
        System.out.println("\nГ");
        System.out.println("передача значения типа лямбды из переменной lambdaN в метод getLambda: " + 
                getLambda(lambdaN, 8).getClass().getSimpleName());
        //ссылка на интерфейс: LambdasBasics$$Lambda/0x00007ad0bc002bf8

        // д) получение переменных / значений в результате вызова метода
        //     * -> метод возвращает указатель на функцию
        System.out.println("\nД");
        IIntegerKeeper calcSquare = getLambda(lambdaN, 8); 
        System.out.println("тип в runtime - ссылка на интерфейс: " + calcSquare.getClass().getSimpleName());
        System.out.println("результат вычисления: " + calcSquare.get()); // отложенное вычисление
        //тип в runtime - ссылка на интерфейс: LambdasBasics$$Lambda/0x000075cd3c003808
        //результат вычисления: 64

        System.out.println("=== завершение ===");
    }

    /**
     * принимает функциональный интерфейс
     * выполняет лямбда-выражение с параметром
     * возвращает объект лямбда-выражения
     * @return
     */
    public static IIntegerKeeper getLambda(IIntegerAction action, int value) {
        return () -> action.perform(value);
    }
    
}
