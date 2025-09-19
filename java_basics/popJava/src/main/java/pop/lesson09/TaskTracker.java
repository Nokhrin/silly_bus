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
 * - дата/время создания
 * - статус - выполнена/не выполнена
 * ---
 * - объект задачи является экземпляром класса LinkedHashMap
 * - структура объекта задачи
 * {"id", String},
 * {"наименование", String},
 * {"владелец", String},
 * {"статус", boolean},
 * {"дата/время создания", Date},
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
 * 
 * 
 * Реализация
 * 0. Системные данные
 *      - перечень команд
 *          @see <a href="java_basics/popJava/src/main/java/pop/lesson09/TaskTracker.java#L142">поддерживаемые команды</a>
 *          - методы выполнения действий по имени команды
*           -
 * 1. Считывание аргументов командной строки
 * 2. Определение действия, заданного аргументами
 * 3. Выполение действия, заданного аргументами
 *      - загрузка файла
 *         - чтение файла
 *         - преобразование считанных данных
 *      - печать помощи
 *      - печать версии
 * 4. Запуск меню
 * 5. Взаимодействие с пользователем
 *     - считывание команды
 *      @see <a href="java_basics/popJava/src/main/java/pop/lesson09/TaskTracker.java#L225">проверка команды</a>
 *     - валидация команды
 *     -
 * 6. Выполнение команды
 *      - создание задачи
         * @see <a href="java_basics/popJava/src/main/java/pop/lesson09/TaskTracker.java#L207">получение ввода</a>
 *      - печать задачи
 *      - изменение задачи
 *      - удаление задачи
 *      
 *      
 * 
 * 
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
        put("/print-task", "print-task");
        put("/pt", "print-task");
        put("/delete-task", "delete-task");
        put("/dt", "delete-task");
        // список задач
        put("/create-list", "create-list");
        put("/cl", "create-list");
        put("/edit-list", "edit-list");
        put("/el", "edit-list");
        put("/print-list", "print-list");
        put("/pl", "print-list");
        put("/delete-list", "delete-list");
        put("/dl", "delete-list");
        // экспорт/импорт
        put("/w", "write");
        put("/r", "read");
    }};

    /**
     * Точка входа
     * @param args  имя программы и параметры запуска
     */
    public static void main(String[] args) {
        PrintStream writer = new PrintStream(System.out);

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
                List<String> dumpData = readFromFile(fileName);
            } else {
                printHeader(writer);
                runCommand(initArgument);
                return;
            }
        }

        logger.debug("Приветствие");
        printHeader(writer);
        printMenu(writer);


        logger.debug("Список задач по умолчанию");
        LinkedHashMap<String, Object> tasksListAll = buildList("1", "Все задачи");
        logger.debug("Создал список задач по умолчанию: " + tasksListAll);

        logger.debug("Загружен файл с задачами");
        // TODO: 23.09.2025

        logger.debug("Создаю поток ввода");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        logger.debug("Запрос ввода");
        String line;
        try {
            writer.print("\nВведи команду (/h для печати доступных команд): ");
            writer.flush();
            while ((line = reader.readLine()) != null) {
                logger.debug(String.format("Введено: %s", line));

                if (line.trim().isEmpty()) {
                    logger.debug("Введена пустая строка");
                    writer.print("Введена пустая строка - Завершаю выполнение\n");
                    writer.flush();
                    break;
                }
                char firstChar = line.charAt(0);
                if (firstChar != '/' || !isValidCommand(line)) {
                    writer.printf("%s - не команда, попробуй ещё\n", line);
                    writer.flush();
                    continue;
                }

                runCommand(line);
            }
        } catch (IOException e) {
            logger.error(String.format("Ошибка ввода:\n%s", e));
        }
// TODO: 24.09.2025 
//  создание + вывод + изменение для задачи
//      в задаче не ссылаться на список
//  создание + вывод + изменение для списка
        


        logger.debug("Завершил выполнение метода main Трекера задач");
    }

    /**
     * Проверяю, поддерживается ли команда
     * @param line
     * @return
     */
    public static boolean isValidCommand(String line) {
        return innerOperations.containsKey(line);
    }

    /**
     * Возвращаю содержимое файла
     *
     * @param fileName директория файла относительно корня проекта / расположения JAR
     * @return список строк
     */
    public static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Файл %s не найден\n%s", fileName, e));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Ошибка чтения файла %s\n%s", fileName, e));
        }
    }

    /**
     * Записываю список строк в файл
     *
     * @param   data     строки для записи
     * @param   filePath директория файла относительно корня проекта / расположения JAR
     */
    public static void writeToFile(List<String> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + filePath, e);
        }
        logger.debug(String.format("Записал файл %s", filePath));
    }

    /**
     * Выполняет запрошенную команду
     * @param cmdName   имя команды
     */
    public static void runCommand(String cmdName) {
        String operationRequired = innerOperations.get(cmdName);
        logger.debug(String.format("Запрошена операция %s", operationRequired));

        // TODO: 25.09.2025  
        // как иначе можно реализовать определение функции для вызова?
        //  хранить ссылку на функцию в словаре?

        if (operationRequired.equals("create-task")) createTask();
        // TODO: 25.09.2025  
        if (operationRequired.equals("edit-task")) editTask();
        if (operationRequired.equals("print-task")) printTask();
        if (operationRequired.equals("delete-task")) deleteTask();
        if (operationRequired.equals("create-list")) createList();
        if (operationRequired.equals("edit-list")) editList();
        if (operationRequired.equals("print-list")) printList();
        if (operationRequired.equals("delete-list")) deleteList();
        if (operationRequired.equals("write")) exportTasks();
        if (operationRequired.equals("read")) importTasks();
        if (operationRequired.equals("version")) printVersion(System.out);
        if (operationRequired.equals("help")) printHelp(System.out);
    }

    private static void importTasks() {
    }

    private static void exportTasks() {
    }

    private static void deleteList() {
    }

    private static void printList() {
    }

    private static void editList() {
    }

    private static void createList() {
    }

    private static void deleteTask() {
    }

    private static void printTask() {
    }

    private static void editTask() {
    }

    static LinkedHashMap<String, Object> createTask() {
        String id = UUID.randomUUID().toString();
        String owner = System.getProperty("user.name");
        LocalDateTime creation_time = LocalDateTime.now();
        boolean done = false;
        String name = getInput("Имя задачи: ");
        LinkedHashMap<String, Object> newTask = buildTask(id, name, owner, creation_time, done);
        logger.debug(String.format("Создана задача %s", newTask));
        return newTask;
    }

    static String getInput(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает и печатает справку по командам меню
     * @param ps
     */
    public static void printHelp(PrintStream ps) {
        // TODO: 24.09.2025 - создавать меню динамически
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
                /w              записать в файл
                /r              прочитать из файла
                """;
        ps.print(helpMessage);
        ps.flush();
    }

    public static void printVersion(PrintStream ps) {
        String version = """
                v0.0.1
                """;
        ps.print(version);
        ps.flush();
    }

    public static void printHeader(PrintStream ps) {
        String appHeader = """
            Трекер задач
            ============
            """;
        ps.print(appHeader);
        ps.flush();
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
     * Печатаю на консоль меню программы
     */
    public static void printMenu(PrintStream ps) {
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
    static LinkedHashMap<String, Object> buildList(String id, String name) {
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
     * {"creation_time", LocalDateTime},
     * {"done", boolean},
     */
    static LinkedHashMap<String, Object> buildTask(String id, String name, String owner, LocalDateTime creation_time, boolean done) {

        LinkedHashMap<String, Object> task = new LinkedHashMap<>();
        task.put("id", id);
        task.put("name", name);
        task.put("owner", owner);
        task.put("creation_time", creation_time);
        task.put("done", done);

        return task;
    }

    /**
     * Преобразую список строк из дампа в список задач
     * @param tasksDump список строк
     * @return          отображение id задачи на экземпляр задачи
     *
     * {
     * {"id", String} ->
     *      {
     *          {"id", String},
     *          {"name", String},
     *          {"owner", String},
     *          {"done", boolean},
     *          {"creation_time", LocalDateTime},
     *          {"start_time", LocalDateTime},
     *          {"finish_time", LocalDateTime}
 *          },
 *     }
     */
    public static LinkedHashMap<Integer, LinkedHashMap<String, Object>> parseTasksDump(String[] tasksDump) {
        // TODO: 23.09.2025
        return new LinkedHashMap<>();
    }

}
