package Lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

interface StringFunction {
    String run(String string);
}

public class Basics {

    public static void main(String[] args) {
        // статический метод
        Function<String, Integer> parser = Integer::parseInt;
        System.out.println(parser.apply("123")); // 123

        // экземплярный метод
        Function<String, Integer> len = String::length;
        System.out.println(len.apply("123")); // 3

        // конструктор
        Function<CharSequence, StringBuilder> charSequenceSupplier = StringBuilder::new;
        // создание экземпляра
        StringBuilder text = charSequenceSupplier.apply("hello");
        System.out.println(text); // hello

        // лямбда как параметр метода ForEach
        ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(1, 2, 3 ,4));
        System.out.println("\nлямбда как параметр");
        nums.forEach((num) -> {System.out.println(num);});
        //1
        //2
        //3
        //4

        // лямбда как значение переменной
        // применяется Consumer - функциональный интерфейс из стандартной библиотеки
        Consumer<Integer> method = (x) -> { System.out.println(x); };
        System.out.println("\nлямбда как значение переменной");
        nums.forEach(method);
        //1
        //2
        //3
        //4

        // объявляем лямбду
        StringFunction format1 = (s) -> s + "!";
        StringFunction format2 = (s) -> s + "?";
        System.out.println(getFormatted("hello", format2)); // hello?
        System.out.println(getFormatted("hello", format1)); // hello!
    }

    // format - экземпляр интерфейса с единственным методом
    // => в качестве значения format допускается лямбда-функция
    public static String getFormatted(String string, StringFunction format) {
        String result = format.run(string); // вызов лямбды
        return result;
    }
}
