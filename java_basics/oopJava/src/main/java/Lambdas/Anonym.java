package Lambdas;

interface Greeting {
    void sayHello();
}

public class Anonym {
    public static void main(String[] args) {
        // реализация интерфейса с помощью анонимного класса
        Greeting greetingClass = new Greeting() {
            public void sayHello() {
                System.out.println("вызов из анонимного класса");
            }
        };
        greetingClass.sayHello(); // вызов из анонимного класса

        // передача лямбда-выражения в качестве реализации анонимного метода
        Greeting greetingLambda = () -> System.out.println("вызов из лямбда-выражения");
        greetingLambda.sayHello(); // вызов из лямбда-выражения
    }
}
