import java.util.Arrays;

public class SurfaceAreaAndVolumeOfaBox {
    public static int[] getSize(int w, int h, int d) {
        /**
         * Принимает три значения
         * Все значения целочисленные, >= 0
         * Возвращает одномерный массив, содержащий два целых числа
         */

        int boxSurfArea = 2*w*h + 2*h*d + 2*w*d;
        int boxVol = w*h*d;

        int[] result = new int[2];
        result[0] = boxSurfArea;
        result[1] = boxVol;

        return result;
    }

    public void testLowerBound() {
        assert Arrays.equals(this.getSize(1, 1, 1), new int[] {6, 1});
    }
    public void testHeightBound() {
        assert Arrays.equals(this.getSize(1, 2, 1), new int[] {10, 2});
    }
    public void testDepthBound() {
        assert Arrays.equals(this.getSize(1, 2, 2), new int[] {16, 4});
    }
    public void testMid() {
        assert Arrays.equals(this.getSize(4, 2, 6), new int[] {88, 48});
    }

    public static void main(String[] args) {
        SurfaceAreaAndVolumeOfaBox boxAreaVol = new SurfaceAreaAndVolumeOfaBox();

        // tests
        boxAreaVol.testLowerBound();
        boxAreaVol.testHeightBound();
        boxAreaVol.testDepthBound();
        boxAreaVol.testMid();
    }
}


/**
 * чему я научился
 * - объявление массива с фиксированным количеством элементов, запись элементов
 * - импорт модуля
 * - метод сравнения массивов
 */
