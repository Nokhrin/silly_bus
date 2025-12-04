package pop.lesson01;

/**
 1. Создайте `Main.java`, выведите `Привет, [Имя]! Сегодня [число] урок Java.`
 2. Добавьте вывод длины имени и сумму `7 + 3`.
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
        // Печатает в stdout строку, определенную заданием
        String userName = "[Имя]";
        int lessonNumber = 1;
        System.out.println(getGreeting(userName, lessonNumber));

        // Печатает в stdout длину имени и сумму `7 + 3`.
        System.out.println(userName.length());
        System.out.println(7 + 3);
    }

}