package pop.lesson04;

import java.io.PrintStream;
import java.util.*;
import java.util.logging.Logger;


public class ScannerInput {
    public static Logger logger = Logger.getLogger(ScannerInput.class.getName());

    // команды вызова справки
    public static final LinkedHashSet<String> printHelpCommands = new LinkedHashSet<>() {{
        add("/help");
        add("/h");
    }};

    // команды добавления значений
    public static final LinkedHashSet<String> addRecordCommands = new LinkedHashSet<>() {{
        add("/add");
        add("/a");
        add("/new");
        add("/n");
    }};

    // команды отображения сохраненных значений
    public static final LinkedHashSet<String> printRecordsCommands = new LinkedHashSet<>() {{
        add("/print");
        add("/p");
        add("/show");
        add("/s");
    }};

    // команды завершения работы программы
    public static final LinkedHashSet<String> exitCommands = new LinkedHashSet<>() {{
        add("/exit");
        add("/ex");
        add("/quit");
        add("/q");
    }};

    /**
     * Возвращает имя команды
     */
    public static String getCommand(Scanner scanner, PrintStream ps) {
        ps.print("Введи команду, нажми Enter (/help для отображения доступных команд): ");
        return scanner.nextLine().trim();
    }

    /**
     * Считывает и возвращает имя
     */
    public static String getName(Scanner scanner, PrintStream ps) {
        ps.print("Введи имя пользователя, нажми Enter: ");
        return scanner.nextLine().trim();
    }

    /**
     * Считывает и возвращает возраст
     */
    public static int getAge(Scanner scanner, PrintStream ps) {
        while (true) {
            try {
                ps.print("Введи возраст, нажми Enter: ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException errInt) {
                ps.println("Введенное значение не является целым числом");
            }
        }
    }

    /**
     * Формирует строковое представление элементов упорядоченного множества
     *
     * @param lhs: множество строковых объектов
     * @return строка, которая объединяет переданные строки символом новой строки
     * другими словами, каждый объект из переданного множества выводится на новой строке
     */
    public static String formatLinkedHashSet(LinkedHashSet<String> lhs) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String item : lhs) joiner.add(item);
        return joiner.toString();
    }

    /**
     * Печатает приветственное сообщение в stdout
     */
    public static void printWelcome(PrintStream ps) {
        String welcoming = """
                Программа создает словарь имя->возраст
                и позволяет добавить записи в словарь
                
                Чтобы начать добавление записи, выполни /add
                Чтобы посмотреть существующие записи, выполни /show
                Для вывода всех поддерживаемых комманд, выполни /help
                
                """;
        ps.print(welcoming);
    }

    /**
     * Печатает приветственное сообщение в stdout
     */
    public static void printHelp(PrintStream ps) {
        String help = """
                ==========
                Поддерживаемые команды
                """ +
                "\nдобавить запись:\n" + formatLinkedHashSet(addRecordCommands) +
                "\n" +
                "\nнапечатать сохраненные записи:\n" + formatLinkedHashSet(printRecordsCommands) +
                "\n" +
                "\nзавершить работу приложения:\n" + formatLinkedHashSet(exitCommands) +
                "\n" +
                "\nнапечатать это сообщение:\n" + formatLinkedHashSet(printHelpCommands) +
                "\n";
        ps.print(help);
    }

    /**
     * Направляет текстовое представление коллекции в указанный поток
     */
    public static void printRecords(SortedMap<String, Integer> recordsCollection, PrintStream ps) {
        if (recordsCollection.isEmpty()) {
            ps.print("Каталог записей пуст");
        } else {
            ps.print(recordsCollection);
        }
    }

    /**
     * Задача -
     * Чтение данных из stdin,
     * валидация значений,
     * выполнение операций над данными,
     * вывод результата
     * ---
     * Требования
     * 1. Запросите имя и возраст
     * 2. сохраните в `Map`.
     * 3. Обработайте ошибку ввода не-числа через `try/catch`.
     * 4. Все методы static
     */
    public static void main(String[] args) {
        // сканер stdin
        Scanner scanner = new Scanner(System.in);

        // поток stdout
        PrintStream printStream = new PrintStream(System.out, true);

        // поток stderr
        PrintStream errorStream = new PrintStream(System.err, true);

        // приветствие
        printWelcome(printStream);

        // формируемый словарь
        SortedMap<String, Integer> users = new TreeMap<>();

        // чтение из stdin
        // читать данные до получения команды останова ввода/программы
        String command;
        while (true) {
            try {
                command = getCommand(scanner, printStream);

                if (printHelpCommands.contains(command)) {
                    printHelp(printStream);
                } else if (printRecordsCommands.contains(command)) {
                    printRecords(users, printStream);
                } else if (addRecordCommands.contains(command)) {
                    users.put(getName(scanner, printStream), getAge(scanner, printStream));
                } else if (exitCommands.contains(command)) {
                    System.exit(0);
                }
            } catch (IllegalStateException err) {
                errorStream.printf("Сканер закрыт или недоступен:\n%s", err.getMessage());
            }
        }
    }
}
