/** В cli-игре персонаж перемещается слева направо.
 * Игрок бросает кубик 1-6, 
 * умножает выпавшее значение на 2,
 * перемещает персонажа на количество позиций, равное полученному числу
 */
public class Move {
    /** Document method
     *      ??? применить Range ???
     * @param position - текущая позиция
     *      допустимые значения [0, Integer.MAX_VALUE - 12]
     * @param roll - число, выпавшее на кубике
     *      допустимые значения [1, 6]
     * @return позиция после перемещения
     */
    public static int move(int position, int roll) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("Стартовая позиция должна быть >= 0");
        }
        if (position > Integer.MAX_VALUE - 12) {
            throw new IllegalArgumentException("Стартовая позиция должна быть <= 2147483635");
        }
        return position + 2*roll;

    }
    public void testJumpFromLowest() {
        assert this.move(0, 4) == 8;
    }
    public void testJumpMinFromHighest() {
        assert this.move(Integer.MAX_VALUE - 12, 1) == 2147483637;
    }
    public void testJumpMaxFromHighest() {
        assert this.move(Integer.MAX_VALUE - 12, 6) == Integer.MAX_VALUE;
    }
    public void testHighestRoll() {
        assert this.move(10, 6) == 22;
    }
    public void testLowestRoll() {
        assert this.move(1, 1) == 3;
    }

    public static void main(String[] args) {
        Move moveHero = new Move();
        moveHero.testJumpFromLowest();
        moveHero.testHighestRoll();
        moveHero.testLowestRoll();
        moveHero.testJumpMinFromHighest();
        moveHero.testJumpMaxFromHighest();
    }
}

/** Чему я научился
 * впервые применил выброс исключений
 *   - дока по теме - https://docs.oracle.com/javase/tutorial/essential/exceptions/index.html
 *
 * продолжил практиковать tdd
 *   - в следующей задаче применю JUnit
 *
 * узнал о существовании систем сборки для Java
 *   - Maven
 *   - Gradle
 *   - Ant
 * ознакомился с документацией систем сборки
 */
