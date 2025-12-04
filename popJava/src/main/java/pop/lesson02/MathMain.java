package pop.lesson02;

import java.math.BigInteger;

/**
 * 1. Реализуйте `sum(int a, int b)` и `isEven(int n)`.
 * 2. Напишите итеративный `factorial(int n)`.
 */
public class MathMain {
    /**
     * Возвращает сумму двух целых чисел
     * @param a - целое число №1
     * @param b - целое число №2
     * @return - целое число, сумма a и b
     */
    public static int sum(int a, int b) {
        return a + b;
    }

    /**
     * Определяет, является ли число четным
     * @param n - проверяемое целое число
     * @return - true для четного, false для нечетного
     */
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    /**
     * Возвращает факториал числа, итеративный алгоритм, цикл for
     * @param n - целое число, для которого рассчитывается факториал n!
     * @return - целое число - факториал n
     */
    public static BigInteger factorialIterative(int n) {
        if (n < 0) throw new IllegalArgumentException("Число должно быть большим либо равным нулю");
        BigInteger fact = BigInteger.ONE;
        /* инвариант - для числа i
        переменная fact содержит значение факториала числа (i-1)
         */
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    /**
     * Возвращает факториал числа, рекурсивный алгоритм
     * @param n - целое число
     * @return - целое число - факториал n
     */
    public static BigInteger factorialRecursive(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) == -1) throw new IllegalArgumentException("Число должно быть большим либо равным нулю");
        /* инвариант - для числа i
        переменная fact содержит значение факториала числа (i-1)
        база рекурсии - для (n == 0) факториал == 1
        шаг рекурсии - возвращает факториал числа (i-1)
         */
        if (n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        return factorialRecursive(n.subtract(BigInteger.ONE)).multiply(n);
    }
}
