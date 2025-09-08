package pop.lesson01;

/**
 * Типы данных
 * - примитивы
 * - объекты - только
 *   - String
 *   - Array
 *   - Collection
 *   <p>
 * Базовые операции
 */
public class StringBasics {
    /**
     * Возвращает приветствие для указанных значений
     */
    public static String getGreeting(String userName, int lessonNumber) {
        return String.format("Привет, %s! Сегодня %d урок Java.", userName, lessonNumber);
    }

    /**
     * Создает и возвращает строку на основе введенной строки
     * @param numberString - состоит из десятичных цифр / [0-9]
     * @return строка, в которой
     *  количество символов равно количеству символов numberString
     *  цифре в numberString, по значению меньшей 5, соответствует цифра 0
     *  цифре в numberString, по значению большей либо равной 5,  соответствует цифра 1
     */
    public static String convertToBinary(String numberString) {
        StringBuilder strAsArray = new StringBuilder();

        for (int i = 0; i < numberString.length(); i++) {
            int num = Character.getNumericValue(numberString.charAt(i));

            if (num < 5) {
                strAsArray.append('0');
            } else {
                strAsArray.append('1');
            }
        }
        return strAsArray.toString();
    }

    public static void main(String[] args) {
        String userName = "[Имя]";
        System.out.println(userName.length());

        int lessonNumber = 1;

        System.out.println(getGreeting(userName, lessonNumber));
    }

}

