package Lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class NestedLambdas {
    public static void main(String[] args) {
        List<String> abc = Arrays.asList("a", "b", "c");
        // первая лямбда получает объект из stream
        // первая лямбда имеет тело, в котором реализует интерфейс Function<T, R>, где T - String, R - String
        // задача - передача данных потока во внутреннюю логику, возврат результата внутренней логики
        List<String> upperList = abc.stream() // создание потока
                .map(letter -> {
                    // вторая лямбда реализует интерфейс Function<T, R>, где T - String, R - String
                    // задача - вернуть строку в верхнем регистре
                    Function<String, String> toUpper = s -> s.toUpperCase();
                    return toUpper.apply(letter); // передаем значение letter функции, описанной в лямбде - то есть, вызываем .toUpperCase() для letter
                })
                .toList();

        System.out.println(upperList); // [A, B, C]

        // вариант решения - методическая ссылка
        List<String> upper2 = abc.stream()
                .map(String::toUpperCase) // методическая ссылка
                .toList();
        System.out.println(upper2); // [A, B, C]
    }
}
