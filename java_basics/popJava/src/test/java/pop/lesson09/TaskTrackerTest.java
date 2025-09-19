package pop.lesson09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static pop.lesson09.TaskTracker.*;

public class TaskTrackerTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskTrackerTest.class);

    @Test
    public void checkLoggerConfig() {
        URL config = getClass().getClassLoader().getResource("simplelogger.properties");
        System.out.println("Config location: " + config);
    }

    @Test
    public void testPrintMenu() {
        final String expected = """
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

        logger.debug("Подменяю стандартный поток вывода");
        ByteArrayOutputStream mockedOutContent = new ByteArrayOutputStream();
        PrintStream mockedOut = new PrintStream(mockedOutContent);

        printMenu(mockedOut);

        String actual = mockedOutContent.toString();

        logger.debug(String.format("ожидание: %s", expected));
        logger.debug(String.format("реальность: %s", actual));

        assertEquals(actual, expected);
    }

    @Test
    public void testPrintVersion() {
        final String expected = """
            v0.0.1
            """;

        logger.debug("Подменяю стандартный поток вывода");
        ByteArrayOutputStream mockedOutContent = new ByteArrayOutputStream();
        PrintStream mockedOut = new PrintStream(mockedOutContent);

        printVersion(mockedOut);

        String actual = mockedOutContent.toString();

        logger.debug(String.format("ожидание: %s", expected));
        logger.debug(String.format("реальность: %s", actual));

        assertEquals(actual, expected);
    }

    @Test
    public void testPrintHeader() {
        final String expected = """
            Трекер задач
            ============
            """;

        logger.debug("Подменяю стандартный поток вывода");
        ByteArrayOutputStream mockedOutContent = new ByteArrayOutputStream();
        PrintStream mockedOut = new PrintStream(mockedOutContent);

        printHeader(mockedOut);

        String actual = mockedOutContent.toString();

        logger.debug(String.format("ожидание: %s", expected));
        logger.debug(String.format("реальность: %s", actual));

        assertEquals(actual, expected);
    }

    @Test
    public void testPrintHelp() {
        final String expected = """
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
            /l              загрузить из файла
            """;

        logger.debug("Подменяю стандартный поток вывода");
        ByteArrayOutputStream mockedOutContent = new ByteArrayOutputStream();
        PrintStream mockedOut = new PrintStream(mockedOutContent);

        printHelp(mockedOut);

        String actual = mockedOutContent.toString();

        logger.debug(String.format("ожидание: %s", expected));
        logger.debug(String.format("реальность: %s", actual));

        assertEquals(actual, expected);
    }

    @Test
    public void testCreateTaskNewOpened() {
        String id = String.valueOf(UUID.randomUUID());
        String name = "Задача Новая Неначатая";
        String owner = "User 1";
        boolean done = false;
        LocalDateTime creation_time = LocalDateTime.now();
        LocalDateTime start_time = null;
        LocalDateTime finish_time = null;

        LinkedHashMap<String, Object> expected = new LinkedHashMap<>();
        expected.put("id", id);
        expected.put("name", name);
        expected.put("owner", owner);
        expected.put("done", done);
        expected.put("creation_time", creation_time);
        expected.put("start_time", start_time);
        expected.put("finish_time", finish_time);

        LinkedHashMap<String, Object> actual = createTask(id, name, owner, done, creation_time, start_time, finish_time);

        assertEquals(actual, expected);
    }

    @Test
    public void testCreateTaskListEmpty() {
        String id = String.valueOf(UUID.randomUUID());
        String name = "Задача 1";

        LinkedHashMap<String, Object> expected = new LinkedHashMap<>();
        expected.put("id", id);
        expected.put("name", name);
        expected.put("tasks", new ArrayList<>());

        LinkedHashMap<String, Object> actual = createTaskList(id, name);

        assertEquals(actual, expected);
    }

    @Test
    public void testMainNoArgs() {
        String[] args = new String[0];
        main(args);
    }

    @Test
    public void testMainVersionArg() {
        String[] args = new String[1];
        args[0] = "-v";
        main(args);
    }

    @Test
    public void testMainHelpArg() {
        String[] args = new String[1];
        args[0] = "-h";
        main(args);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Ключ -l неизвестен и не будет обработан")
    public void testGetArgUnknownKey() {
        String[] args = new String[1];
        args[0] = "-l";
        main(args);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Для команды чтения необходимо указать имя файла")
    public void testGetArgReadFileNoName() {
        String[] args = new String[1];
        args[0] = "-r";
        main(args);
    }

    @Test
    public void testGetArgReadFileWithName() {
        String[] args = new String[2];
        args[0] = "-r";
        args[1] = "TaskTrackerData.dump";
        main(args);
    }

    @Test
    public void testMainImportFileNotExists() {
        String[] args = new String[2];
        args[0] = "-r";
        args[1] = "TaskTrackerData.dump";
        main(args);
    }


}