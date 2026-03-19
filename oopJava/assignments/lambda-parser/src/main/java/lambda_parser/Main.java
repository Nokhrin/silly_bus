package main.java.lambda_parser;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        String name = "lambda";

        Consumer<String> lambda = (n) -> System.out.println(n + ", " + name); 
        
        lambda.accept(("hello"));
        
        // name = "world";
        // изменение значения захваченной переменной вызовет ошибку
        // java: local variables referenced from a lambda expression must be final or effectively final
    }
}
