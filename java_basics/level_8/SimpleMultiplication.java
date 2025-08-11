/* Новая информация
 * условный оператор
 * тернарный оператор
 * javadoc
 */
public class SimpleMultiplication {
    /*
     * Задача
     * Дано целое число N
     * Если N четное, вернуть результат произведения 8 * N
     * Если N нечетное, вернуть результат произведения 9 * N
     *
     */

    public int simpleMultiplication(int n) {
        return (Math.abs(n) % 2 == 0) ? 8*n : 9*n;
    }

    // tests
    public void testZero() {
        assert this.simpleMultiplication(0) == 0;
    }
    public void testBoundarySmallNegative() {
        assert this.simpleMultiplication(-1) == -9;
    }
    public void testBoundarySmallPositive() {
        assert this.simpleMultiplication(1) == 9;
    }
    public void testBoundaryMidNegative() {
        assert this.simpleMultiplication(-23) == -207;
    }
    public void testBoundaryMidPositive() {
        assert this.simpleMultiplication(21) == 189;
    }
    public void testUpperIntLimit() {
        assert this.simpleMultiplication(238609293) == Integer.MAX_VALUE - 10;
    }
    public void testBottomIntLimit() {
        assert this.simpleMultiplication(-268435456) == Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        SimpleMultiplication simpleMult = new SimpleMultiplication();

        simpleMult.testZero();
        simpleMult.testBoundarySmallNegative();
        simpleMult.testBoundarySmallPositive();
        simpleMult.testBoundaryMidNegative();
        simpleMult.testBoundaryMidPositive();
        simpleMult.testUpperIntLimit();
        simpleMult.testBottomIntLimit();
    }


}

