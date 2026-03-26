package lambda_parser;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        String name = "lambda";

        Consumer<String> lambda = (n) -> System.out.println(n + ", " + name); 
        
        lambda.accept(("hello"));
        
        // name = "world";
        // изменение значения захваченной переменной вызовет ошибку
        // java: local variables referenced from a lambda expression must be final or effectively final
        
        
        
        
        // TODO
        // Сборка парсеров в единый объект
        //   Теперь собрать парсер
        //   integer { binary_operator integer}
        //   Допустим есть уже парсеры:
        //   Parser < Integer> intР = ...
        //   Parser < Whitespace> wsP =...
        //   Parser < BinaryOperator> binР =...
        //   Начать с простых конструкций:
        //   integer binary_operator
        //   Результат такого простого парсера record R1(Integer n, Binaryoperator о)
        //   Должно быть примерно так
        //   intP.plus( binP ).map( tuple - > new R1( tuple.a(), tuple.b() ) )
        //   После по пробовать собрать такую конструкцию
        //   integer { binary_operator integer}
    }
}
