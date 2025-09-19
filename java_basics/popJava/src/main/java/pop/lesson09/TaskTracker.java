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
 * {"задачи", Mapping},
 * ---
 *
 * задача - LinkedHashMap<String, Object>
 * список задач - LinkedHashMap<String, LinkedHashMap<String, Object>>
 * список списков задач - LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>>
 *
 *
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
    static final Logger logger = LoggerFactory.getLogger(TaskTracker.class);

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
        put("/exit", "exit");
        put("/ex", "exit");
        put("/quit", "exit");
        put("/q", "exit");
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

    static String ALL_TASKS_LIST_NAME = "Все задачи";

    /**
     * Точка входа
     * @param args  имя программы и параметры запуска
     */
    public static void main(String[] args) {
        logger.debug("Начал выполнение метода main Трекера задач");

        logger.debug("Все списки задач");
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists = new LinkedHashMap<>();

        logger.debug("Список Все задачи");
        allLists.put(ALL_TASKS_LIST_NAME, new LinkedHashMap<>());

        // 
        // 
        // 
        logger.debug("Обработка параметров запуска");
        String initArgument = getArg(args);
        logger.debug("Получен аргумент: " + initArgument);

        if (initArgument != null) {
            if (initArgument.equals("read")) {
                // работа с импортированными данными
                logger.debug("Передан ключ чтения файла");
                logger.debug("Следующим аргументом должно быть имя файла");
                if (args.length == 1) {
                    throw new IllegalArgumentException("Для команды чтения необходимо указать имя файла");
                }
                String fileName = args[1];
                logger.debug(String.format("Запрошена загрузка из файла %s", fileName));
                List<String> dumpData = readFromFile(fileName);

                logger.debug(String.format("Загружен файл с задачами %s", fileName));
                
                logger.debug(String.format("Загружен файл с задачами %s", fileName));
                // TODO: 25.09.2025
                //
            }
        }

        //
        // меню приложения
        //
        logger.debug("Создаю буфер записи потока вывода / stdout");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        logger.debug("Создаю буфер чтения потока ввода / клавиатуры");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        logger.debug("Приветствие");
        printHeader(writer);
        printMenu(writer);

        String userInput;
        try {
            while (true) {
                userInput = getInput("\n/h для печати доступных команд\nПустая строка + Enter -> завершение работы\nВведи команду: ", writer, reader);

                if (userInput.trim().isEmpty()) {
                    logger.debug("Введена пустая строка");
                    writer.write("Введена пустая строка - Завершаю выполнение\n");
                    writer.flush();
                    break;
                }
                char firstChar = userInput.charAt(0);
                if (firstChar != '/' || !isValidCommand(userInput)) {
                    writer.write(String.format("%s - не команда, попробуй ещё\n", userInput));
                    writer.flush();
                    continue;
                }

                logger.debug("Определяю запрошенное действие");
                String operationRequired = getOperation(userInput);
                // NOTE: задача требует процедурного подхода
                //  появлялась идея вызывать функцию с помощью лямбда выражения
                //  хорошая ли это идея?
                switch (operationRequired) {
                    case "exit" -> {
                        return;
                    }
                    case "help" -> {
                        printHelp(writer);
                    }
                    case "create-task" -> {
                        createTask(allLists, writer, reader);
                    }
                    case "print-task" -> {
                        printTask(allLists, writer, reader);
                    }
                    case "edit-task" -> {
                        editTask(allLists, writer, reader);
                    }
                    case "delete-task" -> {
                        deleteTask(allLists, writer, reader);
                    }
                    case "create-list" -> {
                        createList(allLists, writer, reader);
                    }
                    case "print-list" -> {
                        printList(allLists, writer, reader);
                    }
                    case "edit-list" -> {
                        editList(allLists, writer, reader);
                    }
                    case "delete-list" -> {
                        deleteList(allLists, writer, reader);
                    }
                }

//                if (operationRequired.equals("delete-task")) deleteTask();
//                if (operationRequired.equals("create-list")) createList("");
//                if (operationRequired.equals("edit-list")) editList();
//                if (operationRequired.equals("delete-list")) deleteList();
//                if (operationRequired.equals("write")) exportTasks();
//                if (operationRequired.equals("read")) importTasks();
//                if (operationRequired.equals("version")) printVersion(writer);
//                if (operationRequired.equals("help")) printHelp(writer);



            }
        } catch (IOException e) {
            logger.error(String.format("Ошибка буфера записи:\n%s", e));
            throw new RuntimeException(e);
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
    public static String getOperation(String cmdName) {
        String operationRequired = innerOperations.get(cmdName);
        logger.debug(String.format("Запрошена операция %s", operationRequired));
        return operationRequired;
    }

    static void importTasks() {
    }

    static void exportTasks() {
    }

    static void deleteList(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> lists, BufferedWriter bw, BufferedReader br) {
        try {
            bw.write("Существуют списки " + lists);
            bw.write(System.lineSeparator());
            bw.flush();

            String listToDelete = getInput("Введи имя списка, который надо удалить", bw, br);
            logger.debug("Введена строка {}", listToDelete);

            if (listToDelete.equals("Все списки задач")) {
                bw.write("Это корневой список, его нельзя удалить");
                return;
            }

            bw.write("Удаляю " + listToDelete);
            bw.write(System.lineSeparator());
            bw.write("Существуют списки " + lists);
            bw.write(System.lineSeparator());
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void editList(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> lists, BufferedWriter bw, BufferedReader br) {
        try {
            bw.write("Существуют списки " + lists);
            bw.write(System.lineSeparator());
            bw.flush();

            String listToDelete = getInput("Введи имя списка, который надо редактировать", bw, br);
            logger.debug("Введена строка {}", listToDelete);

            // TODO: 29.09.2025

            bw.write("Существуют списки " + lists);
            bw.write(System.lineSeparator());
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void printList(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> lists, BufferedWriter bw, BufferedReader br) {
        try {
            bw.write("Существуют списки " + lists);
            bw.write(System.lineSeparator());
            bw.flush();

            String listToPrint = getInput("Введи имя списка, который надо напечатать", bw, br);
            logger.debug("Введена строка {}", listToPrint);

            bw.write("Список " + listToPrint);
            bw.write(System.lineSeparator());
            bw.write(String.valueOf(lists.get(listToPrint)));
            bw.write(System.lineSeparator());
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавляю задачу в список задач
     * @param task      объект задачи
     * @param listName  имя списка задач, в который включать задачу
     * @param allLists  все списки задач
     */
    static void addTaskToList(LinkedHashMap<String, Object> task, String listName, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists) {
        logger.debug("Добавляю задачу {} в список {}", task, allLists.get(listName));
        allLists.get(listName).put((String) task.get("name"), task);
    }


    /**
     * Инициализирую Список задач, то есть,
     * создаю карту id списка задач -> пустой список задач
     *
     * @return hashmap
     * {"name", String},
     * {"tasks", Mapping},
     */
    static LinkedHashMap<String, LinkedHashMap<String, Object>> buildList(String listName, LocalDateTime creation_time, LocalDateTime modification_time) {

        LinkedHashMap<String, LinkedHashMap<String, Object>> newTaskList = new LinkedHashMap<>();
        newTaskList.put(listName, new LinkedHashMap<>());
        newTaskList.get(listName).put("creation_time", creation_time);
        newTaskList.get(listName).put("modification_time", modification_time);
        newTaskList.get(listName).put("tasks", new LinkedHashMap<>());

        return newTaskList;
    }

    static void createList(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists, BufferedWriter bw, BufferedReader br) {
        LocalDateTime creation_time = LocalDateTime.now();
        LocalDateTime modification_time = creation_time;
        String name = getInput("Имя списка задач: ", bw, br);
        LinkedHashMap<String, LinkedHashMap<String, Object>> newTaskList = buildList(name, creation_time, modification_time);
        logger.debug(String.format("Создан список задач %s", newTaskList));

        logger.debug("Добавить список задач {} в коллекцию списков задач", newTaskList);
        allLists.put(name, newTaskList);
        logger.debug("Общий список задач\n{}", allLists);
    }

    static void deleteTask(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists, BufferedWriter bw, BufferedReader br) {
        LinkedHashMap<String, LinkedHashMap<String, Object>> allTasks = allLists.get(ALL_TASKS_LIST_NAME);
        try {
            bw.write("Для удаления задачи введи имя задачи и нажми Enter");
            bw.write(System.lineSeparator());
            bw.write("Для отмены удаления оставь пустую строку и нажми Enter");
            bw.write(System.lineSeparator());
            bw.flush();

            String taskToDelete;
            while (true) {
                taskToDelete = br.readLine();
                if (taskToDelete == null || taskToDelete.trim().isEmpty()) {
                    bw.write("Выхожу из режима редактирования");
                    break;
                } else if (allTasks.containsKey(taskToDelete)) {
                    bw.write("Текущее состояние задачи " + taskToDelete + ":");
                    bw.write(System.lineSeparator());
                    bw.write(String.valueOf(allTasks.get(taskToDelete)));
                    bw.flush();
                    return;
                } else {
                    bw.write("Задачи " + taskToDelete + " не существует") ;
                    bw.write(System.lineSeparator());
                    bw.write("Попробуй еще");
                    bw.write(System.lineSeparator());
                    bw.flush();
                }
            }
            logger.debug("Введена строка {}", taskToDelete);

            bw.write(System.lineSeparator());
            bw.write(String.valueOf(allTasks.remove(taskToDelete)));
            bw.write("Удаление завершено");
            bw.write(System.lineSeparator());
            bw.write("Текущее состояние списков задач: ");
            bw.write(System.lineSeparator());
            bw.write(String.valueOf(allLists));
        } catch (IOException e) {
            logger.error("Ошибка чтения ввода {}", e.toString());
            throw new RuntimeException(e);
        }
    }

    static void printTask(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists, BufferedWriter bw, BufferedReader br) {
        LinkedHashMap<String, LinkedHashMap<String, Object>> allTasks = allLists.get(ALL_TASKS_LIST_NAME);
        try {
            bw.write("Существуют задачи");
            bw.write(System.lineSeparator());
            bw.write(String.valueOf(allTasks));
            bw.write(System.lineSeparator());
            bw.write("Для печати задачи введи Имя задачи и нажми Enter");
            bw.write(System.lineSeparator());
            bw.write("Для отмены печати оставь пустую строку и нажми Enter");
            bw.write(System.lineSeparator());
            bw.flush();

            String taskToPrint;
            while (true) {
                taskToPrint = br.readLine();
                logger.debug("Введена строка {}", taskToPrint);
                if (taskToPrint == null || taskToPrint.trim().isEmpty()) {
                    bw.write("Выхожу из режима редактирования");
                    break;
                } else if (allTasks.containsKey(taskToPrint)) {
                    bw.write("Текущее состояние задачи " + taskToPrint + ":");
                    bw.write(System.lineSeparator());
                    bw.write(String.valueOf(allTasks.get(taskToPrint)));
                    bw.flush();
                    return;
                } else {
                    bw.write("Задачи " + taskToPrint + " не существует") ;
                    bw.write(System.lineSeparator());
                    bw.write("Попробуй еще");
                    bw.write(System.lineSeparator());
                    bw.flush();
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка чтения ввода {}", e.toString());
            throw new RuntimeException(e);
        }
    }

    static void editTask(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists, BufferedWriter bw, BufferedReader br) {
        LinkedHashMap<String, LinkedHashMap<String, Object>> allTasks = allLists.get(ALL_TASKS_LIST_NAME);
        try {
            bw.write("=== Редактирование задачи ===");
            bw.write(System.lineSeparator());
            bw.write("Для начала редактирования введи id задачи и нажми Enter");
            bw.write(System.lineSeparator());

            bw.write("Для завершения редактирования нажми Enter в новой строке");
            bw.write(System.lineSeparator());

            bw.flush();

            String taskIdToEdit;
            while (true) {
                taskIdToEdit = br.readLine();
                if (taskIdToEdit == null || taskIdToEdit.trim().isEmpty()) {
                    bw.write("Выхожу из режима редактирования");
                    break;
                }
            }
            logger.debug("Введена строка {}", taskIdToEdit);
            bw.write("Введена строка " + taskIdToEdit);
            // TODO: 27.09.2025

            bw.write("Текущее состояние задачи " + taskIdToEdit + ":");
            bw.write(System.lineSeparator());
            bw.write(String.valueOf(allTasks.get(taskIdToEdit)));
            bw.write("Для изменения параметра введите имя параметра");
            bw.write(System.lineSeparator());
            bw.flush();

            logger.debug("Читаю ввод");
            String line;
            while (true) {
                line = br.readLine();
                logger.debug("Введена строка {}", line);
                if (taskIdToEdit == null || taskIdToEdit.trim().isEmpty()) {
                    bw.write("Выхожу из режима редактирования");
                    break;
                } else if (line.equals("done")) {
                    bw.write("Запрошено изменение статуса");
                    bw.write(System.lineSeparator());
                    allTasks.get(taskIdToEdit).put("done", true);
                    allTasks.get(taskIdToEdit).put("modification_time", LocalDateTime.now());
                    bw.write("Статус изменен");
                    bw.write(System.lineSeparator());
                } else if (line.equals("owner")) {
                    bw.write("Запрошено изменение владельца");
                    bw.write(System.lineSeparator());

                    // TODO: 29.09.2025
                    allTasks.get(taskIdToEdit).put("owner", "new_owner");

                    allTasks.get(taskIdToEdit).put("modification_time", LocalDateTime.now());
                    bw.write("Владелец изменен");
                    bw.write(System.lineSeparator());
                } else if (line.equals("name")) {
                    bw.write("Запрошено изменение наименования задачи");
                    bw.write(System.lineSeparator());

                    // TODO: 29.09.2025
                    allTasks.get(taskIdToEdit).put("name", "new_name");

                    allTasks.get(taskIdToEdit).put("modification_time", LocalDateTime.now());
                    bw.write("Наименование задачи изменено");
                    bw.write(System.lineSeparator());
                }
                bw.write("Текущее состояние задачи " + taskIdToEdit + ":");
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(allTasks.get(taskIdToEdit)));
                bw.write(System.lineSeparator());
                bw.flush();
            }


        } catch (IOException e) {
            logger.error("Ошибка чтения ввода {}", e.toString());
            throw new RuntimeException(e);
        }
    }

    static void createTask(LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>> allLists, BufferedWriter bw, BufferedReader br) {
        String owner = System.getProperty("user.name");
        LocalDateTime creation_time = LocalDateTime.now();
        LocalDateTime modification_time = creation_time;
        boolean done = false;
        String name = getInput("Имя задачи: ", bw, br);
        LinkedHashMap<String, Object> newTask = buildTask(name, owner, creation_time, modification_time, done);
        logger.debug(String.format("Создана задача %s", newTask));

        logger.debug("Добавить задачу {} в общий список задач", newTask);
        addTaskToList(newTask, ALL_TASKS_LIST_NAME, allLists);
        logger.debug("Общий список задач\n{}", allLists);
    }

    /**
     * Печатаю сообщение для пользователя, считываю ответный ввод
     * @param prompt
     * @param br
     * @param bw
     * @return
     */
    static String getInput(String prompt, BufferedWriter bw, BufferedReader br) {
        String userInput = null;
        try {
            bw.write(prompt);
            bw.flush();
            userInput = br.readLine();
            logger.debug(String.format("Введено: %s", userInput));

        } catch (IOException e) {
            logger.error(String.format("Ошибка ввода:\n%s", e));
        }
        return userInput;
    }

    /**
     * Создает и печатает справку по командам меню
        // TODO: 24.09.2025 - создавать меню динамически
     // TODO: 25.09.2025 методы вывода сообщений дублируются, меняется только текст, создать один метод с параметром
     * @param bw
     */
    public static void printHelp(BufferedWriter bw) {
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
        try {
            bw.write(helpMessage);
            bw.flush();
        } catch (IOException e) {
            logger.error("Ошибка операции ввода/вывода\n" + e);
        }
    }

    public static void printVersion(BufferedWriter bw) {
        String version = """
                v0.0.1
                """;
        try {
            bw.write(version);
            bw.flush();
        } catch (IOException e) {
            logger.error("Ошибка операции ввода/вывода\n" + e);
        }
    }

    public static void printHeader(BufferedWriter bw) {
        String appHeader = """
            Трекер задач
            ============
            """;
        try {
            bw.write(appHeader);
            bw.flush();
        } catch (IOException e) {
            logger.error("Ошибка операции ввода/вывода\n" + e);
        }
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
    public static void printMenu(BufferedWriter bw) {
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
        try {
            bw.write(menuText);
            bw.flush();
        } catch (IOException e) {
            logger.error("Ошибка операции ввода/вывода\n" + e);
        }
    }

    /**
     * Создаю задачу
     *
     * @return hashmap, представляющий Задачу
     * {"id", String},
     * {"name", String},
     * {"owner", String},
     * {"creation_time", LocalDateTime},
     * {"modification_time", LocalDateTime},
     * {"done", boolean},
     */
    static LinkedHashMap<String, Object> buildTask(String name, String owner, LocalDateTime creation_time, LocalDateTime modification_time, boolean done) {

        LinkedHashMap<String, Object> task = new LinkedHashMap<>();
        task.put("name", name);
        task.put("owner", owner);
        task.put("creation_time", creation_time);
        task.put("modification_time", modification_time);
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
