package org.example.lambdas;

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
//        System.out.println("тип переменной lambdaN: " + lambdaN.getClass().getSimpleName());
        //java: variable lambdaN might not have been initialized => переменная объявлена, не инициализирована
        
        // б) запись в переменную значения типа лямбды
        lambdaN = n -> n*n;
        System.out.println("\nБ");
        System.out.println("тип в runtime - ссылка на объект лямбды: " + lambdaN.getClass().getSimpleName());
        //тип переменной lambdaN: LambdasBasics$$Lambda/0x000076c6f4002bf8
        
        // в) чтение значения типа лямбды из переменной - с целью выполнить метод
        System.out.println("\nВ");
//        lambdaN.perform(5); // вызов метода, реализованного лямбдой
        System.out.println("результат вычисления: " + lambdaN.perform(5)); // вычисление
        //результат вычисления: 25

        // г) передача значения типа лямбды из переменной в метод через стек 
        //     * -> передача указателя на функцию методу
        System.out.println("\nГ");
        getLambda(lambdaN, 8); // опосредованная передача значения лямбде
        System.out.println("тип в runtime - ссылка на интерфейс: " + getLambda(lambdaN, 8).getClass().getSimpleName());
        //ссылка на интерфейс: LambdasBasics$$Lambda/0x00007ad0bc002bf8

        // д) получение переменных / значений в результате вызова метода
        //     * -> метод возвращает указатель на функцию
        System.out.println("\nД");
        IIntegerAction calcSquare = getLambda(lambdaN, 8); 
        System.out.println("тип в runtime - ссылка на интерфейс: " + calcSquare.getClass().getSimpleName());
        //ссылка на интерфейс: LambdasBasics$$Lambda/0x00007ad0bc002bf8
        System.out.println("результат вычисления: " + calcSquare.perform(5)); // вычисление
        //результат вычисления: 25

        // полиморфизм ссылок
        // up cast из типа лямбды к типу object - явное приведение типа с расширением  от Lambda к Object
        System.out.println("\nЕ");
        Object lambdaAsObject = (Object) getLambda(lambdaN, 8);
        System.out.println("тип в runtime - лямбда после upcast: " + lambdaAsObject.getClass().getSimpleName());
        // лямбда после upcast: LambdasBasics$$Lambda/0x00007479dc002bf8

        //down cast из object к типу лямбды
        System.out.println("\nЁ");
        IIntegerAction lambdaAsFuncInterface = (IIntegerAction) lambdaAsObject;
        System.out.println("тип в runtime - лямбда после downcast: " + lambdaAsFuncInterface.getClass().getSimpleName());
        // лямбда после downcast: LambdasBasics$$Lambda/0x00007479dc002bf8
        
        // инициализация лямбда переменной значением на существующий метод
        // variable = this::method 
        // variable = Myclass::static_method
        
        IIntegerAction lambdaVar = LambdasBasics::getFactorial;
        System.out.println("\nЖ");
        System.out.println("лямбда - ссылка на существующий метод: " + lambdaVar.getClass().getSimpleName());
        //лямбда - ссылка на существующий метод: LambdasBasics$$Lambda/0x00007dac44004800
        System.out.println("результат вычисления: " + lambdaVar.perform(5)); // вычисление
        //результат вычисления: 120
        
        System.out.println("=== завершение ===");
    }

    /**
     * принимает функциональный интерфейс
     * выполняет лямбда-выражение с параметром
     * возвращает объект лямбда-выражения
     * @return
     */
    public static IIntegerAction getLambda(IIntegerAction action, int value) {
        return action;
    }

    /**
     * возвращает факториал числа
     */
    public static int getFactorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        }

        return num * getFactorial(num - 1);
    }

}
