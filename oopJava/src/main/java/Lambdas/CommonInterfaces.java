package Lambdas;

import java.util.function.*;

public class CommonInterfaces {
    public static void main(String[] args) {
        // Supplier<T>  - абстрактный метод get
        // не принимает параметры, так как его абстрактный метод get не имеет параметров
        // возвращает значение
        Supplier<String> greeting = () -> "Supplier - hello";
        System.out.println(greeting.get());

        // Consumer<T> - абстрактный метод accept
        // принимает один параметр,
        // выполняет действие,
        // не возвращает значение
        Consumer<String> printer = s -> System.out.println(s);
        printer.accept("Consumer - hello");

        // Function<T, R> - абстрактный метод apply
        // принимает один параметр,
        // возвращает одно значение
        Function<String, Integer> parser = Integer::parseInt;
        System.out.println(parser.apply("123"));

        // BiFunction<T, U, R> - абстрактный метод apply
        // принимает два параметра, типы данных параметров различаются
        // возвращает одно значение
        BiFunction<String, Integer, String> formatter = (name, age) -> "BiFunction - имя: " + name + ", возраст: " + age.toString();
        System.out.println(formatter.apply("username", 123)); // имя: username, возраст: 123

        // Consumer<T> - абстрактный метод accept
        // принимает один параметр,
        // выполняет действие,
        // не возвращает значение
        BiConsumer<String, Integer> biPrinter = (name, age) -> System.out.println("BiConsumer - имя: " + name + ", возраст: " + age.toString());
        biPrinter.accept("user", 321);

        /*
        Supplier - hello
        Consumer - hello
        123
        BiFunction - имя: username, возраст: 123
        BiConsumer - имя: user, возраст: 321
         */
    }
}
