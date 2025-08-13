/**
 * Класс содержит методы преобразования строк
 */
public class AltCase {
    /**
     * Метод инвертирует регистр каждой буквы в переданной строке
     * @param string
     *      Строка, которую требуется преобразовать
     * @return
     *      Преобразованная строка
     *      Функция чистая - функция не изменяет переданный объект, но создает новый
     */
    public static String toAlternativeString(String string) {
        try {
            // todo
        } catch (NullPointerException e) {
            System.out.println("Параметру передан null");
        }
        return "";
    }

    public void testAllLowerToUpper() {
        assert this.toAlternativeString("hello world").equals("HELLO WORLD");
    }
    public void testAllUpperToLower() {
        assert this.toAlternativeString("HELLO WORLD").equals("hello world");
    }
    public void testNonAlpha() {
        assert this.toAlternativeString("12345").equals("12345");
    }
    public void testAlphaAndNonAlpha() {
        assert this.toAlternativeString("1a2b3c4d5e").equals("1A2B3C4D5E");
    }
    public void testEmptyStringUnmod() {
        assert this.toAlternativeString("").equals("");
    }

    public static void main(String[] args) {
        AltCase ac = new AltCase();
        /**
         * tests
         */
        ac.testAllLowerToUpper();
        ac.testAllUpperToLower();
        ac.testNonAlpha();
        ac.testAlphaAndNonAlpha();
    }
}


/**
 * чему я научился
 * - описание параметров функций в javadoc
 * - синтаксис перехвата исключений
 */
