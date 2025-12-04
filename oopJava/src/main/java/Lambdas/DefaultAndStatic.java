package Lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;

public class DefaultAndStatic {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B", "C");
        // spliterator() - default метод
        Spliterator<String> spliterator = list.spliterator();
        System.out.println(spliterator);

        // Map.of() - static метод
        Map<Integer, String> map = Map.of(1, "A", 2, "B");
        System.out.println(map);
    }
}
