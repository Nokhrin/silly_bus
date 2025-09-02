package pop.lesson03;

public class Task1 {
    // по условию
    final int[] target = new int[]{3, 1, 4, 1, 5};

    /**
     * Возвращает максимальное значение из списка
     * @param array - одномерный массив целых чисел
     * @return - целое число
     */
    public int getMax(int[] array) {
        int maxValue = array[0];
        int i = 1;
        while (i < array.length) {
            // для тренировки, на практике лучше применить Math.max()
            maxValue = array[i] > maxValue ? array[i] : maxValue;
            i++;
        }
        return maxValue;
    }

    /**
     * Возвращает среднее арифметическое значений элементов списка
     * Задание требует найти среднее - я выбрал искать среднее арифметическое
     * Средним могут быть другие величины - медиана, значение из середины списка
     * Возвращаемое значение также не описано, сделал по своему усмотрению
     * @param array - одномерный массив целых чисел
     * @return - строковое представление числа с плавающей точкой, дробь округлена до 2х знаков
     */
    public String getAvg(int[] array) {
        int arraySum = 0;
        int i = 0;
        while (i < array.length) {
            arraySum += array[i];
            i++;
        }
        return String.format("%.2f", (float) arraySum / array.length);
    }

}
