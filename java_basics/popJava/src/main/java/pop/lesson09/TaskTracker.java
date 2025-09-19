package pop.lesson09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
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

    /**
     * Точка входа
     * @param args  имя программы и параметры запуска
     */
    public static void main(String[] args) {
        logger.debug("Начал выполнение метода main Трекера задач");

        logger.debug("Проверка команды запуска");
        // TODO: 20.09.2025
        ArrayList<String> initArguments = getArgs(args);
        logger.debug("Получены аргументы: " + initArguments);

        logger.debug("Поток вывода");
        PrintStream streamOut = new PrintStream(System.out, true);
        logger.debug("Создал Поток вывода: " + streamOut);

        logger.debug("Список задач по умолчанию");
        LinkedHashMap<String, Object> tasksListAll = createTaskList("1", "Все задачи");
        logger.debug("Создал список задач по умолчанию: " + tasksListAll);

        logger.debug("Поток вывода");
        Scanner scanner = new Scanner(System.in);
        logger.debug("Создал Поток вывода: " + scanner);

        logger.debug("Чтение ввода");
        String userInput = getInput(scanner);
        logger.debug("Введено значение: " + userInput);

        printMenu(streamOut);
        logger.debug("Завершил выполнение метода main Трекера задач");
    }

    /**
     * Возвращает имена действий из аргументов командой строки
     * @param   cmdArgs  считанные аргументы
     * @return  имя команды
     */
     public static ArrayList<String> getArgs(String[] cmdArgs) {
         logger.debug(String.valueOf(cmdArgs));

         HashMap<String, String> cmdOperations = new HashMap<>() {{
             put("-v", "version");
             put("--version", "version");
             put("--help", "help");
             put("-h", "help");
         }};

         ArrayList<String> commands = new ArrayList<>();

         for (String arg : cmdArgs) {
             logger.debug(arg);
             if (arg.charAt(0) == '-') {
                 if (cmdOperations.containsKey(arg)) {
                     commands.add(arg);
                 } else {
                     logger.error(String.format("Ключ %s неизвестен и не будет обработан", arg));
                 }
             } else {
                 logger.debug("Аргумент не является параметром");
                 logger.debug("Аргумент может быть значением параметра");
                 logger.debug("Например, именем файла");
                 // TODO: 20.09.2025
             }
         }
         return commands;
    }

    /**
     * Выводит на консоль меню программы
     */
    static void printMenu(PrintStream ps) {
        final String menuText = """
                 Трекер задач
                ==============
                
                Команды:
                /ct, /create-task   создать Задачу
                /cl, /create-list   создать Список задач
                """;
        ps.print(menuText);
        ps.flush();
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
