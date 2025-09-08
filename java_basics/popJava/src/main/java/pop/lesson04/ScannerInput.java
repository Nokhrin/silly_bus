package pop.lesson04;

import java.util.*;

/**
 * Задача -
 *  Чтение данных из stdin,
 *  валидация значений,
 *  выполнение операций над данными,
 *  вывод результата
 * ---
 * Требования
 * 1. Запросите имя и возраст
 * 2. сохраните в `Map`.
 * 3. Обработайте ошибку ввода не-числа через `try/catch`.
 * 4. Все методы static
 */
public class ScannerInput {
    /**
     * сканер stdin
     */
    private static Scanner scanner;

    /**
     * команды отображения сохраненных значений
     */
    private static final ArrayList<String> showSavedDataCommands = new ArrayList<>() {{
        add("/print");
        add("/p");
        add("/show");
        add("/s");
    }};

    /**
     * команды завершения работы программы
     */
    private static final ArrayList<String> exitCommands = new ArrayList<>() {{
        add("/exit");
        add("/ex");
        add("/quit");
        add("/q");
    }};

    /**
     * формируемый словарь
     */
    private static HashMap<String, Integer> usernameAge = new HashMap<>();

    /**
     * Читает ввод, печатает введенную информацию, не сохраняет
     * Блокирует поток I/O из-за ожидания ввода значения
     * 1) "баннер"
     * 2) приглашение ввода
     * 3) ввод в цикле
     * 4) отдельная команда завершения ввода
     * @return
     */
    public static HashMap<String, Integer> getUserInput() {
        // "баннер"
        doWelcome();

        // обработка ввода
        try {
            scanner = new Scanner(System.in);
            processCliInput();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return usernameAge;
    }

    /**
     * Читает ввод пользователя, обрабатывает и записывает готовые значения в хранилище
     * @return
     */
    private static void processCliInput() {
        // читать данные до получения команды останова ввода/программы
        while (true) {
            try {
                // имя
                System.out.print("Введи имя пользователя или команду, нажми Enter: ");
                System.out.flush();
                String userInput = scanner.nextLine().trim();

                if (showSavedDataCommands.contains(userInput)) {
                    System.out.printf("Словарь содержит записи:\n%s\n", usernameAge);
                    continue;
                } else if (exitCommands.contains(userInput)) {
                    System.out.println("Останавливаю выполнение программы");
                    System.out.flush();
                    System.exit(0);
                }

                String userName = userInput;

                // возраст
                System.out.printf("Введи возраст пользователя %s, нажми Enter: ", userName);
                System.out.flush();
                int userAge = getIntInput();

                // очистка буфера
                scanner.nextLine();

                // запись пары "имя:возраст" в словарь
                usernameAge.put(userName, userAge);
            } catch (IllegalStateException err) {
                System.err.printf("Сканер закрыт или недоступен:\n%s", err.getMessage());
                System.out.flush();
            }
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException err) {
                System.out.printf("Введенное значение %s не является целым числом, повторите ввод: ", err.getMessage());
                System.out.flush();
                scanner.nextLine();
            }
        }
    }

    /**
     * Формирует строковое представление элементов списка
     * @param al
     * @return
     */
    private static String getArrayListItems(ArrayList<String> al) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String item : al) joiner.add(item);
        return joiner.toString();
    }

    public static void doWelcome() {
        String welcoming = """
                Программа создает словарь имя->возраст
                
                ==========
                Первым запрашивается значение поля `имя` - значение должно быть строкой
                Вторым запрашивается значение поля `возраст` - значение должно быть целым числом
                
                По запросу в консоли введи соответствующее значение
                Чтобы сохранить значение и перейти к вводу следующего, нажми Enter
                
                Пробельные символы в начале и в конце введенного значения отбрасываются
                Чтение значение возраста выполняется, пока не будет получено целое число
                
                Процедуру чтения данных можно прервать с помощью Поддерживаемых команд
                Поддерживаемые команды выполняются только при вводе значения `имя`
                
                ==========
                Поддерживаемые команды
                """ +
                "\nнапечатать элементы созданного словаря:\n" + getArrayListItems(showSavedDataCommands) +
                "\n" +
                "\nзавершить работу приложения:\n" + getArrayListItems(exitCommands) +
                "\n";
        System.out.println(welcoming);
        System.out.flush();
    }

    public static void main(String[] args) {

        System.out.println(getUserInput());
    }

}
