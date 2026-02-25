package org.example.oop_modeling;

/**
 * Лямбда-функции - базовые операции
 */
public class MethodModel {
    /**
     * работы с лямбдами как с переменными
     */
    public static void main(String[] args) {
        System.out.println("=== начало ===");
        IIntegerAction lambdaN; // объявление переменной
        lambdaN = n -> n*n;

        // полиморфизм ссылок
        // up cast из типа лямбды к типу object - явное приведение типа с расширением  от Lambda к Object
        System.out.println("\nЕ");
        Object lambdaAsObject = (Object) getLambda(lambdaN, 8);
        System.out.println("тип в runtime - объект, реализующий интерфейс IIntegerAction - лямбда после upcast: " + lambdaAsObject.getClass().getSimpleName());
        // лямбда после upcast: LambdasBasics$$Lambda/0x00007479dc002bf8

        //down cast из object к типу лямбды + успешный вызов
        System.out.println("\nЁ");
        IIntegerAction lambdaDowncastSuccess = (IIntegerAction) lambdaAsObject;
        System.out.println("тип в runtime - приведение от Object к IIntegerAction - ожидается успех, так как исходный тип IIntegerAction: " + lambdaDowncastSuccess.getClass().getSimpleName());
        System.out.println("лямбда после downcast: " + lambdaDowncastSuccess.getClass().getSimpleName());
        System.out.println("успешный вызов после downcast: " + lambdaDowncastSuccess);
        //тип в runtime - приведение от Object к IIntegerAction - ожидается успех, так как исходный тип IIntegerAction: LambdasBasics$$Lambda/0x00007ffb38002c00
        //лямбда после downcast: LambdasBasics$$Lambda/0x00007ffb38002c00
        //успешный вызов после downcast: org.example.lambdas.LambdasBasics$$Lambda/0x00007ffb38002c00@4783da3f
        
        //down cast из object к типу лямбды + НЕуспешный вызов
        System.out.println("\nЖ");
        Object notLambdaVar = Integer.valueOf(1);
        try {
            System.out.println("компиляция успешна так как тип Object, runtime - ожидается провал приведения, так как исходный тип Integer, а не IIntegerAction");
            IIntegerAction lambdaDowncastFailure = (IIntegerAction) notLambdaVar;
            System.out.println("лямбда после downcast: " + lambdaDowncastFailure.getClass().getSimpleName());
        } catch (ClassCastException e) {
            System.err.println("ошибка downcast: " + e);
        }
        //компиляция успешна так как тип Object, runtime - ожидается провал приведения, так как исходный тип Integer, а не IIntegerAction
        //ошибка downcast: java.lang.ClassCastException: class java.lang.Integer cannot be cast to class org.example.lambdas.IIntegerAction 
        
        // инициализация лямбда переменной значением на существующий метод
        System.out.println("\nЗ");
        // статический метод
        // variable = Myclass::static_method
        IIntegerAction lambdaVar = num -> getFactorial(num);
        System.out.println("Myclass::static_method -> " + lambdaVar.getClass().getSimpleName());
        //лямбда - ссылка на существующий метод: LambdasBasics$$Lambda/0x00007dac44004800
        System.out.println("результат вычисления: " + lambdaVar.perform(5)); // вычисление
        //Myclass::static_method -> LambdasBasics$$Lambda/0x00007f41f8004800
        //результат вычисления: 120

        System.out.println("\nИ");
        // метод экземпляра конкретного объекта
        // variable = object::instance_method 
        MethodModel lambdasBasics = new MethodModel();
        IIntegerAction lambdaVarObjInstance = num -> lambdasBasics.getCube(num);
        System.out.println("object::instance_method -> " + lambdaVarObjInstance.getClass().getSimpleName());
        System.out.println("результат вычисления: " + lambdaVarObjInstance.perform(4));
        //object::instance_method -> LambdasBasics$$Lambda/0x00007f41f8004a18
        //результат вычисления: 64

        System.out.println("\nК");
        // метод экземпляра текущего объекта
        // variable = this::instance_method 
        MethodModel instance = new MethodModel();
        System.out.println("this::instance_method -> " + instance.getClass().getSimpleName());
        System.out.println("результат вычисления: " + instance.testCube(5));
        //this::instance_method -> LambdasBasics
        //результат вычисления: 125

        System.out.println("\nЛ");
        // variable = Class::instance_method 
        IStringToInt strLenRef = String::length;
        IStringToInt strLenLambda = s -> s.length();
        System.out.println("Class::instance_method -> " + strLenRef.getClass().getSimpleName());
        System.out.println("результат вычисления: " + strLenRef.convert("123"));
        
        System.out.println("s -> s.length(): " + strLenRef.getClass().getSimpleName());
        System.out.println("результат вычисления: " + strLenLambda.convert("123"));
        //Class::instance_method -> LambdasBasics$$Lambda/0x000072588c0059f8
        //результат вычисления: 3
        //s -> s.length(): LambdasBasics$$Lambda/0x000072588c0059f8
        //результат вычисления: 3

        System.out.println("=== завершение ===");
    }

    /**
     * возвращает куб
     */
    private int getCube(int num) {
        return num * num * num;
    }

    /**
     * ссылается на метод текущего экземпляра
     */
    public int testCube(int n) {
        IIntegerAction lambdaVarThis = num -> getCube(num);
        return  lambdaVarThis.perform(n);
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
    private static int getFactorial(int num) {
//    public static int getFactorial(int num) {
        if (num == 0 || num == 1) {
            return 1;
        }

        return num * getFactorial(num - 1);
    }
    
}
