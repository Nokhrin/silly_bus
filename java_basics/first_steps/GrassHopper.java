/**
 * На вход передается число N
 * Вернуть сумму всех натуральных чисел в [1, N]
 */
public class GrassHopper {
    /**
     * Задача сложение первых N натуральных чисел
     * Сумма арифметической прогрессии с шагом 1
     * S = (a1 + aN) * N / 2
     * 
     * частный случай
     * S = (1 + N) * N / 2
     */
    public static int summation(int n) {
        return (1 + n) * n / 2;
    }

    public void testLowerBound() {
        assert this.summation(1) == 1;
    }
    public void testMid() {
        assert this.summation(8) == 36;
    }
    public void testUpperBound() {
        assert this.summation(Integer.MAX_VALUE) == -1073741824;
    }

    public static void main(String[] args) {
        GrassHopper gh = new GrassHopper();
        gh.testLowerBound();
        gh.testMid();
        gh.testUpperBound();
    };
}
