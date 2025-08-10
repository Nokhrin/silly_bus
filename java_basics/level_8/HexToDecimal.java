/**
 * Преобразование шестнадцатиричного/hex числа в десятичное
 *
 * Разработать функцию, которая
 * 1. На вход принимает строку, представляющую hex число
 * 2. Преобразует hex число в десятичное
 * 3. Возвращает десятичное число как объект int
 *
 * @author Nokhrin
 * 
 */

public class HexToDecimal {
    public static int hexToDec(final String hexString) {
        /**
         * @param hex число, String
         * @return десятичное число, int
         * @see <a href="https://www.codewars.com/kata/57a4d500e298a7952100035d">источник</a>
         */
        return 0;
    }

    /**
    * тесты
    */
    public void testZero() {
        assert this.hexToDec("0") == 0;
    }
    public void testBelowZero() {
        assert this.hexToDec("1") == 1;
    }
    public void testAboveZero() {
        assert this.hexToDec("1") == 1;
    }
    public void testMidPositiveLowerCase() {
        assert this.hexToDec("a") == 10;
    }
    public void testMidNegativeUpperCase() {
        assert this.hexToDec("-C") == -12;
    }
    public void testLowerBound() {
        assert this.hexToDec("-FF") == -255;
    }
    public void testUpperBound() {
        assert this.hexToDec("FF") == 255;
    }

    /**
    * entry point
    */
    public static void main(String[] args) {
        HexToDecimal hdc = new HexToDecimal();

        // tests
        hdc.testZero();
        hdc.testBelowZero();
        hdc.testAboveZero();
        hdc.testMidPositiveLowerCase();
        hdc.testMidNegativeUpperCase();
        hdc.testLowerBound();
        hdc.testUpperBound();
    }
}


/**
 * чему я научился в ходе работы
 * 
 * - базовый синтаксис JavaDoc
 *
 * - проверка гипотезы в jshell
 * <pre>{@code
 *   jshell> int dec = Integer.parseInt("FF", 16);
 *   dec ==> 255
 *   }
 * </pre>
 */

/*
 * обычный многострочный коммент
 */

/**
 * многострочный коммент JavaDoc
 */

