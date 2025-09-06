package pop.lesson02;

import java.math.BigInteger;

/**
 * 1. Реализуйте `sum(int a, int b)` и `isEven(int n)`.
 * 2. Напишите итеративный `factorial(int n)`.
 */
public class Main {
    /**
     * Возвращает сумму двух целых чисел
     * @param a - целое число №1
     * @param b - целое число №2
     * @return - целое число, сумма a и b
     */
    public int sum(int a, int b) {
        return a + b;
    }

    /**
     * Определяет, является ли число четным
     * @param n - проверяемое целое число
     * @return - true для четного, false для нечетного
     */
    public boolean isEven(int n) {
        return n % 2 == 0;
    }

    /**
     * Возвращает факториал числа, итеративный алгоритм, цикл for
     * @param n - целое число, n <= 20 - максимум для итога типа long
     * @return - целое число - факториал n
     * задача не говорит требования к типу результата, возможны варианты - long и BigInteger
     * вариант с BigInteger определим как ответ на задание
     */
    public BigInteger factorial(int n) {
        BigInteger fact = BigInteger.valueOf(1);
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    public long factorialIterativeForLoopLong(int n) {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public int factorialIterativeForLoopInt(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    /**
     * Возвращает факториал числа, рекурсивный алгоритм
     * @param n - целое число
     * @return - целое число - факториал n
     */
    public long factorialRecursiveLong(int n) {
        if (n == 1) {
            return 1;
        }
        return factorialRecursiveLong(n - 1) * n;
    }
}
