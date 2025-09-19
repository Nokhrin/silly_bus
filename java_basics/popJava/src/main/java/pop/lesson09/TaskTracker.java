package pop.lesson09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Консольный трекер задач
 * ---
 * Трекер является системой управления задачами
 * ---
 * Сущность "Задача"
 * Минимальная единица, которой манипулирует трекер
 * Задача содержит атрибуты
 * - уникальный идентификатор / id
 * - наименование
 * - владелец
 * - статус - выполнена/не выполнена
 * - дата/время создания
 * - дата/время начала выполнения
 * - дата/время завершения выполнения
 * ---
 * - объект задачи является экземпляром класса LinkedHashMap
 * - структура объекта задачи
 * {"id", String},
 * {"наименование", String},
 * {"владелец", String},
 * {"статус", boolean},
 * {"дата/время создания", Date},
 * {"дата/время начала выполнения", Date},
 * {"дата/время завершения выполнения", Date},
 * - ссылка на объект задачи существует только в области видимости метода main
 * - не хранится в полях классов
 * ---
 * Задача поддерживает операции
 * - создание
 * - изменение
 * - удаление
 * - печать текстового представления
 * ---
 * Сущность "Список задач"
 * Задачи объединяются в списки задач
 * - объект Список задач является экземпляром класса LinkedHashMap
 * - структура объекта Список задач
 * {"id списка задач", String},
 * {"имя списка задач", String},
 * {"задачи", ArrayList[{Задача_1} , {Задача_2}, [...], {Задача_N}]},
 * ---
 * Список задач поддерживает операции
 * - создание
 * - изменение
 * - удаление
 * - печать текстового представления
 * - запись в файл
 * - чтение из файла
 * ---
 * Задача всегда включена в список задач
 * Все задачи включены в список "Все задачи"
 * ---
 * Договоренности
 * стиль процедурный
 * tdd
 * для каждого метода существует юнит-тест
 * ---
 * Пользовательский интерфейс
 * командная строка
 * ---
 * Ввод/вывод
 * - Меню через `Scanner` (п.4).
 * - Сохранение в `tasks.csv` через `BufferedWriter` (п.8).
 * ---
 * Обработка ошибок / перехват исключений
 * ---
 * Управление зависимостями
 * добавить пакет `slf4j`
 * ---
 * Логирование
 * применить `slf4j`
 * ---
 * Сборка
 * имя пакета `tracker.jar`
 * shell скрипт сборки
 * сборка Maven
 * ---
 * Критерии проверки
 * - При запуске `java -jar tracker.jar` выводится меню.
 * - Данные сохраняются в `tasks.csv` и загружаются при повторном запуске.
 * - Обработка ошибок: некорректный ввод числа, отсутствие файла.
 * ---
 * Установка проекта для разработчика
 * выполняется клонирование репозитория
 * выполняется установка зависимостей
 */
public class TaskTracker {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }
    private static final Logger logger = LoggerFactory.getLogger(TaskTracker.class);

    public static HashMap<String, String> cmdOperations = new HashMap<>() {{
        put("--read", "read");
        put("-r", "read");
        put("--help", "help");
        put("-h", "help");
        put("--version", "version");
        put("-v", "version");
    }};

    public static HashMap<String, String> innerOperations = new HashMap<>() {{
        // общие
        put("/help", "help");
        put("/h", "help");
        put("/version", "version");
        put("/v", "version");
        // задача
        put("/create-task", "create-task");
        put("/ct", "create-task");
        put("/edit-task", "edit-task");
        put("/et", "edit-task");
        // список задач
        put("/create-list", "create-list");
        put("/cl", "create-list");
        // экспорт/импорт
        put("/w", "write");
        put("/r", "read");
    }};

    /**
     * Точка входа
     * @param args  имя программы и параметры запуска
     */
    public static void main(String[] args) {
        logger.debug("Начал выполнение метода main Трекера задач");

        logger.debug("Проверка команды запуска");
        // TODO: 20.09.2025
        String initArgument = getArg(args);
        logger.debug("Получен аргумент: " + initArgument);

        if (initArgument != null) {
            if (initArgument.equals("read")) {
                logger.debug("Передан ключ чтения файла");
                logger.debug("Следующим аргументом должно быть имя файла");
                if (args.length == 1) {
                    throw new IllegalArgumentException("Для команды чтения необходимо указать имя файла");
                }
                String fileName = args[1];
                logger.debug(String.format("Запрошена загрузка из файла %s", fileName));
                ArrayList<String> dumpData = readTasksDump(fileName);
            } else {
                printHeader();
                runCommand(initArgument);
                System.exit(0);
            }
        }

        // TODO: 20.09.2025 - надо ?
//        logger.debug("Поток вывода");
//        PrintStream streamOut = new PrintStream(System.out, true);
//        logger.debug("Создал Поток вывода: " + streamOut);

        logger.debug("Приветствие");
        printHeader();
        printMenu();

        // если не загружен файл
        logger.debug("Список задач по умолчанию");
        LinkedHashMap<String, Object> tasksListAll = createTaskList("1", "Все задачи");
        logger.debug("Создал список задач по умолчанию: " + tasksListAll);

        logger.debug("Поток ввода");
        Scanner scanner = new Scanner(System.in);
        logger.debug("Создал Поток ввода: " + scanner);

        logger.debug("Чтение ввода");
        String userInput = getInput(scanner);
        logger.debug("Введено значение: " + userInput);

        logger.debug("Завершил выполнение метода main Трекера задач");
    }

    /**
     * Возвращаю содержимое файла
     *
     * @param fileName директория файла относительно корня проекта / расположения JAR
     * @return список строк
     */
    public static ArrayList<String> readTasksDump(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            logger.error(String.format("Файл %s не найден", fileName));
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Ошибка чтения файла");
            throw new RuntimeException(e);
        }
    }

    /**
     * Выполняет запрошенную команду
     * @param cmdName   имя команды
     */
    public static void runCommand(String cmdName) {
        logger.debug(cmdName);
        if (cmdName.equals("version")) printVersion();
        if (cmdName.equals("help")) printHelp();
    }

    public static void printHelp() {
        String helpMessage = """
                Операции
                
                Списки задач
                /pl             напечатать все
                /cl list_name   создать
                /el list_id     редактировать
                /dl list_id     удалить
                
                Задачи
                /pt             напечатать все
                /ct task_name   создать
                /et task_id     редактировать
                /dt task_id     удалить
                
                Экспорт/импорт всех задач
                /s              выгрузить в файл
                /l              загрузить из файла""";
        System.out.println(helpMessage);
        System.out.flush();
    }

    public static void printVersion() {
        String version = """
                v0.0.1
                """;
        System.out.println(version);
        System.out.flush();
    }

    public static void printHeader() {
        String appHeader = """
            Трекер задач
            ============
            """;
        System.out.println(appHeader);
        System.out.flush();
    }

    /**
     * Возвращает имена действий из аргументов командой строки
     * @param   cmdArgs  считанные аргументы
     * @return  имя команды
     */
     public static String getArg(String[] cmdArgs) throws IllegalArgumentException {
         if (cmdArgs.length > 2) {
             throw new IllegalArgumentException("Превышено допустимое количество параметров");
         }
         if (cmdArgs.length == 0) return null;

         String arg = cmdArgs[0];
         logger.debug(arg);
         if (cmdOperations.containsKey(arg)) {
             return cmdOperations.get(arg);
         } else {
             throw new IllegalArgumentException(String.format("Ключ %s неизвестен и не будет обработан", arg));
         }
    }

    /**
     * Выводит на консоль меню программы
     */
    public static void printMenu() {
        final String menuText = """
                Программа позволяет
                - создавать, изменять, удалять задачи
                - создавать, изменять, удалять списки задачи
                - выгружать задачи в файл
                
                Программа поддерживает ввод
                - в интерактивном режиме в командой строке
                - из файла определенного формата

                ==============
                Введи команду, нажми Enter
                /h -> все команды
                /v -> версия программы
                """;
        System.out.print(menuText);
        System.out.flush();
    }

    /**
     * Создаю Список задач
     *
     * @return hashmap
     * {"id", String},
     * {"name", String},
     * {"tasks", ArrayList[{Task_1} , {Task_2}, [...], {Task_N}]},
     */
    static LinkedHashMap<String, Object> createTaskList(String id, String name) {
        ArrayList<Map<String, Object>> tasks = new ArrayList<>();

        LinkedHashMap<String, Object> taskList = new LinkedHashMap<>();
        taskList.put("id", id);
        taskList.put("name", name);
        taskList.put("tasks", tasks);

        return taskList;
    }

    /**
     * Создаю задачу
     *
     * @return hashmap, представляющий Задачу
     * {"id", String},
     * {"name", String},
     * {"owner", String},
     * {"done", boolean},
     * {"creation_time", LocalDateTime},
     * {"start_time", LocalDateTime},
     * {"finish_time", LocalDateTime},
     */
    static LinkedHashMap<String, Object> createTask(String id, String name, String owner, boolean done, LocalDateTime creation_time, LocalDateTime start_time, LocalDateTime finish_time) {

        LinkedHashMap<String, Object> task = new LinkedHashMap<>();
        task.put("id", id);
        task.put("name", name);
        task.put("owner", owner);
        task.put("done", done);
        task.put("creation_time", creation_time);
        task.put("start_time", start_time);
        task.put("finish_time", finish_time);

        return task;
    }

    /**
     * Возвращаю имя команды по ключу
     * @param scanner   потока ввода
     * @param cmdKey    псевдоним команды
     * @return          системное имя команды
     */
    public static String getCommand(Scanner scanner, String cmdKey) {

        return "";
    }

    /**
     * Возвращаю содержимое потока ввода
     * @param scanner   поток ввода
     * @return          строка
     */
    private static String getInput(Scanner scanner) {
        return "";
    }

}
