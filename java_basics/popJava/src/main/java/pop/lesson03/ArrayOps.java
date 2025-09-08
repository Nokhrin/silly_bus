package pop.lesson03;

import java.util.ArrayList;

/**
 * Операции над элементами массива
 */
public class ArrayOps {
    /**
     * Возвращает максимальное по значению число из списка
     * @param list - одномерный непустой список объектов
     * @return - строковое представление числа с плавающей точкой, дробь округлена до 2х знаков
     */
    public static String getMaxValue(ArrayList<?> list) {
        validateArrayList(list);

        double maxValue = Double.MIN_VALUE;

        /* инвариант
        значение переменной maxValue
        на шаге i больше либо равен
        числу в массиве list в диапазоне индексов [0; i]
        */
        for (Object num : list) {
            if (num instanceof Integer || num instanceof Double || num instanceof Float) {
                double currNum = Double.parseDouble(num.toString());
                if (currNum > maxValue) {
                    maxValue = currNum;
                }
            }
        }

        return String.format("%.2f", maxValue);
    }

    /**
     * Возвращает среднее арифметическое значений элементов списка
     * Задание требует найти среднее - я выбрал искать среднее арифметическое
     * Средним могут быть другие величины - медиана, значение из середины списка
     * Возвращаемое значение также не описано, сделал по своему усмотрению
     * @param list - одномерный массив целых чисел
     * @return - строковое представление числа с плавающей точкой, дробь округлена до 2х знаков
     */
    public static String getAvgValue(ArrayList<?> list) {
        validateArrayList(list);

        int count = 0;
        double total = 0;
        for (Object num : list) {
            if (num instanceof Integer || num instanceof Double || num instanceof Float) {
                total += Double.parseDouble(num.toString());
                count++;
            }
        }
        if (count == 0) {
            throw new ArithmeticException("Ошибка! Деление на ноль, переданный список не содержит числовых элементов");
        }

        return String.format("%.2f", total / count);
    }

    /**
     * Проверяет соответствие фактических свойств списка ожидаемым
     * @param list
     * @return
     */
    public static void validateArrayList(ArrayList<?> list) {
        if (list == null) {
            throw new NullPointerException("Аргумент должен быть не null");
        }
        if (list.size() == 0) {
            throw new IllegalArgumentException("Список должен быть непустым");
        }
    }
}
