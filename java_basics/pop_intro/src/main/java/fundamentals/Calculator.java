package fundamentals;

public class Calculator {
    /**
     * Возвращает среднее арифметическое элементов массива
     * Для пустого массива возвращает 0
     * @param array
     * @return
     */
    public static double findAverage(int[] array) {
        double result = 0.0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        if (array.length > 0) {
            result /= array.length;
        }
        return result;
    }

    public static double findAverageEnhanced(int[] array) {
        double result = 0.0;
        for (int j : array) {
            result += j;
        }
        if (array.length > 0) {
            result /= array.length;
        }
        return result;
    }
}
/*
Чему я научился
- цикл for
- последовательное чтение элементов
- применение статического метода класса
  - класс используется как хранилище функции
  - при вызове не нужно создавать экземпляр класса, обращаюсь к функции ИмяКласса.Метод
 */