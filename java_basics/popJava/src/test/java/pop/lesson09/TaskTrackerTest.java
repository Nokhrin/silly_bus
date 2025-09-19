package pop.lesson09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static pop.lesson09.TaskTracker.*;

public class TaskTrackerTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskTrackerTest.class);
    @Test
    public void testPrintMenu() {
        final String expected = """
             Трекер задач
            ==============
            """;

        // создаю поток с пустой значением, ввод не предусмотрен
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        PrintStream customStreamOut = new PrintStream(bytesOut);

        printMenu(customStreamOut);

        String actual = bytesOut.toString();

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
}