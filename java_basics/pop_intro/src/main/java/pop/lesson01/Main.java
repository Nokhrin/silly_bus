package pop.lesson01;

import java.util.Scanner;

public class Main {
    /**
     * строго следую условиям
     */
    public static String mainStrict1() {
        return "Привет, [Имя]! Сегодня [число] урок Java.";
    }

    public static int mainStrict2() {
        return "[Имя]".length();
    }

    public static int mainStrict3() {
        return 7 + 3;
    }

    /**
     * фантазирую
     * - отправляю запрос ввести имя в stdout
     * - читаю ввод из stdin
     * - генерирую случайное число для номера урока
     * - поскольку задача процедурная, не функциональная, выполняю в точке входа
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя и нажмите Enter");
        String username = scanner.nextLine();
        int lessonNumber = (int) (Math.random() * 8 + 1); // [1, 9]
        String message = "Привет, " + username + "! Сегодня " + lessonNumber + " урок Java.";
        System.out.println(message);
    }

}

