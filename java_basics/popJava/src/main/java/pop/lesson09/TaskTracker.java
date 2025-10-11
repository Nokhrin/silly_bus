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
 * Пользовательский интерфейс
 * - аргументы командной строки при запуске приложения
 * - интерактивный текстовый режим - после запуска
 * ---
 * WIP - ОБРАБОТКА КОМАНД
 * -общие
 * quit
 * help
 * version
 * <p>
 * -список задач
 * list список задач
 * add добавить задачу
 * delete удалить задачу
 * write экспорт в файл
 * read импорт из файла
 * <p>
 * -задача
 * create создать задачу
 * edit редактировать задачу
 * show напечатать задачу
 * <p>
 * -command line args
 * "--read filepath", "-r filepath" импорт задач из файла
 * "--help", "-h", руководство пользователя
 * "--about" информация о приложении
 */
public class TaskTracker {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    static final Logger logger = LoggerFactory.getLogger(TaskTracker.class);

    /**
     * Точка входа
     *
     * @param args имя программы и параметры запуска
     */
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // пустой экземпляр списка задач
            TasksList tasksList = TasksList.getTasksList();

            // параметры командной строки
            if (args.length == 0) {
                logger.debug("Параметры не получены. Продолжаю работу без параметров");
            } else {
                logger.debug("Разбираю параметры");
                for (String arg : args) {
                    // разбор ключа загрузки задач из файла
                    if ("--about".equals(arg)) {
                        bw.write("Трекер задач");
                        bw.newLine();
                        bw.write("версия 0.1");
                        bw.newLine();
                        return;
                    } else if ("--help".equals(arg) || "-h".equals(arg)) {
                        //  вынести в метод?
                        bw.write("Запуск: java -jar TaskTracker.jar [опции]");
                        bw.newLine();
                        bw.write("--read=путь/к/файлу   Загрузить задачи из файла");
                        bw.newLine();
                        bw.write("--about   Сведения о программе");
                        bw.newLine();
                        bw.write("--help   Помощь");
                        bw.newLine();
                        bw.newLine();
                        bw.write("Пример");
                        bw.newLine();
                        bw.write("java -jar TaskTracker.jar --read=bkp/tasks.csv");
                        bw.newLine();
                        bw.write("java -jar TaskTracker.jar --about");
                        bw.newLine();
                        bw.newLine();
                        bw.flush();
                        return;

                    } else if (arg.startsWith("--read")) {
                        String[] parts = arg.split("=", 2);
                        if (parts.length > 1) {

                            // экземпляр списка задач с задачами из файла
                            String fileName = parts[1];
                            tasksList.fromStore(fileName);
                            break;
                        } else {
                            throw new IllegalArgumentException("Для параметра read не получено значение");
                        }
                    }
                }
            }


            // старт меню
            bw.write(">>> Трекер задач <<<");
            bw.newLine();
            bw.flush();

            String input;

            bw.write("Введите команду - help для вывода справки о командах, quit или пустую строку для завершения работы");
            bw.newLine();
            bw.flush();  // строка будет выведена только при использовании flush
            while ((input = br.readLine()) != null) {
                // чтение команды
                if ("".equals(input.trim()) || "quit".equalsIgnoreCase(input.trim())) {
                    bw.write("Завершаю работу Трекера");
                    bw.newLine();
                    break;
                }

                logger.debug("Получена команда '{}'", input);

                // распознавание команды - вынести в метод?
                // выполнение команды

                // NOTE: умышленно не применил switch, чтобы использовать equalsIgnoreCase
                //  если правильно понимаю, switch выполняет тот же if-else + equals 
                if ("help".equalsIgnoreCase(input)) {
                    bw.write("""
                    -список задач
                    list список задач
                    add добавить задачу
                    delete удалить задачу
                    write экспорт в файл
                    read импорт из файла
                    -задача
                    create создать задачу
                    edit редактировать задачу
                    show напечатать задачу
                    """);
                    bw.newLine();
                    bw.flush();

                } else if ("list".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("Список задач пуст");
                    } else {
                        bw.write(tasksList.toString());
                    }
                    bw.newLine();
                    bw.flush();
                } else if ("add".equalsIgnoreCase(input)) {
                    Task task = new Task();
                    bw.write("Создана задача " + task);
                    bw.newLine();
                    bw.flush();
                } else if ("delete".equalsIgnoreCase(input)) {
                    bw.write("Удалить задачу");
                    bw.newLine();
                    bw.flush();
                } else if ("write".equalsIgnoreCase(input)) {
                    bw.write("Записать задачи в файл");
                    bw.newLine();
                    bw.write("Введи имя файла: ");
                    bw.flush();

                    String filename = br.readLine();
                    if (filename.trim().equals("")) {
                        throw new IOException("В качестве имени файла получена пустая строка");
                    }
                    bw.write("Записать задачи в файл " + filename);
                    bw.newLine();
                    bw.flush();
                } else if ("read".equalsIgnoreCase(input)) {
                    bw.write("Добавить задачи из файла");
                    bw.newLine();
                    bw.write("Введи имя файла: ");
                    bw.flush();

                    String filename = br.readLine();
                    if (filename.trim().equals("")) {
                        throw new IOException("В качестве имени файла получена пустая строка");
                    }
                    bw.write("Добавить задачи из файла " + filename);
                    bw.newLine();
                    bw.flush();
                } else if ("create".equalsIgnoreCase(input)) {
                    bw.write("Создать задачу");
                    bw.newLine();

                    bw.write("Введи имя задачи: ");
                    String taskName = br.readLine();
                    if (taskName.trim().equals("")) {
                        throw new IOException("В качестве статуса задачи получена пустая строка");
                    }
                    bw.newLine();
                    bw.flush();

                    bw.write("Введи владельца задачи: ");
                    String taskOwner = br.readLine();
                    if (taskOwner.trim().equals("")) {
                        throw new IOException("В качестве статуса задачи получена пустая строка");
                    }
                    bw.flush();

                    Task task = new Task(taskName, taskOwner);

                } else if ("edit".equalsIgnoreCase(input)) {
                    bw.write("Редактировать задачу");
                    bw.newLine();
                    bw.write("Введи id задачи: ");
                    bw.flush();

                    String taskId = br.readLine();
                    if (taskId.trim().equals("")) {
                        throw new IOException("В качестве id задачи получена пустая строка");
                    }
                    Task taskToEdit = tasksList.getTasks().get(Integer.parseInt(taskId));

                    bw.write("Задача");
                    bw.newLine();
                    bw.write(taskToEdit.toString());
                    bw.newLine();
                    bw.write("Доступны для редактирования поля: name, owner, done");
                    bw.newLine();

                    bw.write("Введи имя поля для редактирования: ");
                    bw.flush();

                    String fieldName = br.readLine();
                    if (fieldName.trim().equals("")) {
                        throw new IOException("В качестве имени поля получена пустая строка");
                    } else if (fieldName.trim().equalsIgnoreCase("name") ||
                            fieldName.trim().equalsIgnoreCase("owner") ||
                            fieldName.trim().equalsIgnoreCase("done")) {
                        throw new IllegalArgumentException("Получено несуществующее имя поля");
                    }


                    if (fieldName.trim().equalsIgnoreCase("name")) {
                        bw.write("Введи новое имя задачи: ");
                        bw.flush();
                        String value = br.readLine();
                        if (value.trim().equals("")) {
                            throw new IOException("В качестве значения получена пустая строка");
                        }
                        taskToEdit.setName(value);
                    } else if (fieldName.trim().equalsIgnoreCase("owner")) {
                        bw.write("Введи нового владельца задачи: ");
                        bw.flush();
                        String value = br.readLine();
                        if (value.trim().equals("")) {
                            throw new IOException("В качестве значения получена пустая строка");
                        }
                        taskToEdit.setOwner(value);
                    } else if (fieldName.trim().equalsIgnoreCase("done")) {
                        bw.write("Введи y для изменения статуса, введи иное значение для отмены: ");
                        bw.flush();
                        String value = br.readLine();
                        if (!value.trim().equals("y")) {
                            bw.write("Переключение статуса отменено");
                            bw.newLine();
                            bw.flush();
                            break;
                        }
                        if (taskToEdit.isDone()) {
                            taskToEdit.setDone(false);
                        } else {
                            taskToEdit.setDone(true);
                        }
                    }

                    bw.flush();


                    bw.flush();

                } else if ("show".equalsIgnoreCase(input)) {
                    bw.write("Напечатать задачу");
                    bw.newLine();
                    bw.write("Введи id задачи: ");
                    bw.flush();

                    String taskId = br.readLine();
                    if (taskId.trim().equals("")) {
                        throw new IOException("В качестве имени задачи получена пустая строка");
                    }
                    Task taskToPrint = tasksList.getTasks().get(Integer.parseInt(taskId));

                    bw.write("Задача");
                    bw.newLine();
                    bw.write(taskToPrint.toString());
                    bw.flush();
                }

            }

        } catch (IOException e) {
            logger.error("Ошибка ввода / вывода {}", e.toString());
        }
    }
}