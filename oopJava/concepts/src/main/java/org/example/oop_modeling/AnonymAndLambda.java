package org.example.oop_modeling;

public class AnonymAndLambda {
    public static void main(String[] args) {
        // реализовать интерфейс
        // создать анонимный класс
        // вызвать метод
        ISpeakable xAnon = new ISpeakable() {
            public void say() {
                System.out.println("анонимный класс");
            }
        };
        xAnon.say();

        // реализовать интерфейс
        // создать лямбду
        // вызвать метод
        ISpeakable xLambda = () -> System.out.println("лямбда");
        xLambda.say();
    }
}
