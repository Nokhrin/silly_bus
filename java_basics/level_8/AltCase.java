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
        char[] invertedChars = new char[string.length()];
        try {
            for (int i = 0; i < string.length(); i++) {
                char currChar = string.charAt(i);
                if (Character.isLowerCase(currChar)) {
                    currChar = Character.toUpperCase(currChar);
                }
                else {
                    currChar = Character.toLowerCase(currChar);
                }
                invertedChars[i] = currChar;
            }
        } catch (NullPointerException e) {
            System.out.println("Параметру передан null\n" + e);
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            System.out.println("Обращение к индексу вне существующего диапазона\n" + e);
        }
        return new String(invertedChars);
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
 *
 * - базовая отладка
 *   - jdi - программный интерфейс виртуальной машины java (jvm), поддерживающий операции отладки
 * - применение jdb
 *   - определение точек останова
 *   - чтение значений в точках останова
 *
 * - описание параметров функций в javadoc
 *
 * - синтаксис перехвата исключений
 *   - вывод кастомного сообщения при перехвате исключения
 * - организация исключений в java
 *   - исключения есть классы в модуле java.lang
 *   - например, исключение при обращении по несуществующему индексу к элементу строки - java.lang.StringIndexOutOfBoundsException
 */
