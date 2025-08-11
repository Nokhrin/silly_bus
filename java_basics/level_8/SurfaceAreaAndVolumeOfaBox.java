public class SurfaceAreaAndVolumeOfaBox {
    public static int[] getSize(int w, int h, int d) {
        /**
         * Принимает три значения
         * Все значения целочисленные, >= 0
         * Возвращает одномерный массив, содержащий два целых числа
         */

        boxAreaSurface int[] = new int[2];
        return boxAreaSurface;
    }

    public void testLowerBound() {
        assert this.getSize(1, 1, 1) == new int[] {6, 1};
    }
    public void testHeightBound() {
        assert this.getSize(1, 2, 1) == new int[] {10, 2};
    }
    public void testDepthBound() {
        assert this.getSize(1, 2, 2) == new int[] {16, 4};
    }
    public void testMid() {
        assert this.getSize(4, 2, 6) == new int[] {88, 48};
    }

    public static void main() {
        SurfaceAreaAndVolumeOfaBox boxAreaVol = new SurfaceAreaAndVolumeOfaBox();

        // tests
        boxAreaVol.testLowerBound();
        boxAreaVol.testHeightBound();
        boxAreaVol.testDepthBound();
        boxAreaVol.testMid();
    }
}
