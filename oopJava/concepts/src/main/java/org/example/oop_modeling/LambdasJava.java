package org.example.oop_modeling;

/**
 * Функциональный интерфейс
 */
interface MathOp {
    int operate(int a, int b);
}

/**
 *  Полиморфизм поведения через лямбда-выражения и трассировка стека исполнения (call stack)
 */
public class LambdasJava {
    public static void main(String[] args) {
        /**
         * Реализация ФИ
         * Лямбда - экземпляр анонимного класса, 
         * создаваемый jvm в runtime и находящийся в области памяти java heap
         * Анонимный класс содержит код тела лямбды как реализацию метода ФИ
         */
        MathOp sumOp = (a, b) -> a + b;


        System.out.println(sumOp.operate(1, 2));
        
        

        MathOp subOp = (a, b) -> a - b;
        MathOp mulOp = (a, b) -> a * b;
        MathOp divOp = (a, b) -> a / b;
    }
}
