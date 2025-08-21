/**
 * На вход поступает целое число из интервала [0, 9]
 * Вернуть текстовое представление числа
 * Например, 0 -> "Zero"
 *
 * Цели решения задачи
 *   - понять области видимости объектов
 *     - в java "переменные" - это идентификаторы объектов?
 *   - тренировать создание и вызов нескольких методов в одном классе
 *   - тренировать инструкции управления потоком
 *   - if - then - else
 *   - switch
 *   - понять, что такое "java проект"
 *     - добавить описание зависимостей с включением JUnit 5
 *
 * Цели применения юнит-тестов
 *   - практика осмысления архитектуры кода, понимания, осознания порядка поступления и обработки данных программой
 *   - практика выбора техники тест-дизайна (граничные значения, классы эквивалентности)
 *   - практика придумывания оптимального комплекта тестов
 *   - отработка синтаксиса
 *   - разучивание методов примитивных типов (пример, сравнение строк)
 *
 * Материалы
 * https://docs.oracle.com/javase/tutorial/java/data/strings.html
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html
 */
public class FirstSwitch {
    public static String switchItUp_ifThen(int number){
        String num = "";
        return num;
    }

    public static String switchItUp(int number){
        String num = "";
        switch (number) {
            case 0: num = "Zero";
                    break;
            case 1: num = "One";
                    break;
            case 2: num = "Two";
                    break;
            case 3: num = "Three";
                    break;
            case 4: num = "Four";
                    break;
            case 5: num = "Five";
                    break;
            case 6: num = "Six";
                    break;
            case 7: num = "Seven";
                    break;
            case 8: num = "Eight";
                    break;
            case 9: num = "Nine";
                    break;
        }
        return num;
    }

    /**
     * проверяем нижнюю границу области допустимых значений параметра
     */
    public void testLower() {
        assert this.switchItUp(0).equals("Zero");
    }

    /**
     * проверяем значение из середины области допустимых значений параметра
     */
    public void testMid() {
        assert this.switchItUp(4).equals("Four");
    }

    /**
     * проверяем верхнюю границу области допустимых значений параметра
     */
    public void testUpper() {
        assert this.switchItUp(9).equals("Nine");
    }

    public static void main(String[] args) {
        FirstSwitch fsw = new FirstSwitch();
        fsw.testLower();
        fsw.testMid();
        fsw.testUpper();
        //
    }
}

/**
 * Чему я научился
 *
 * String - "простой тип данных", но не примитив
 * ближайший по смыслу примитивный тип - char
 * String - это объект, экземпляр класса String из библиотеки java.lang
 */
