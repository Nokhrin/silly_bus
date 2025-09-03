package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Инструменты для генерации тестовых данных
 */
public class DataGenerators {
    /**
     * Возвращает quantity псевдослучайных целых чисел из интервала [min, max)
     * @param min - число типа int
     * @param max - число типа int
     * @param quantity - натуральное число
     * @return - одномерный массив элементов int
     */
    public static int[] getRandomIntArray(int quantity, int min, int max) {
        // todo - проверка аргументов
        List<Integer> randInts = new ArrayList<Integer>();

        for (int count = 0; count < quantity; count++) {
            randInts.add((int) (Math.random() * (min + max) + min));
        }

        int[] randIntsArray = randInts.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return randIntsArray;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getRandomIntArray(5, 12, 48)));
    }
}
