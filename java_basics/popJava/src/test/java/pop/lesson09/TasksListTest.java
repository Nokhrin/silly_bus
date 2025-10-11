package pop.lesson09;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.testng.Assert.assertThrows;

@Test
public class TasksListTest {

    private TasksList tasksList;

    @BeforeMethod
    public void setUp() {
        tasksList = new TasksList(); // Каждый тест — свой экземпляр
    }

    @Test(description = "addTask должен добавлять задачу в список")
    public void testAddTask() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);

        Assert.assertTrue(tasksList.contains(task));
        Assert.assertEquals(tasksList.size(), 1);
    }

    @Test(description = "addTask задачи могу иметь одинаковые имена")
    public void testAddTaskDuplicate() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);
        tasksList.addTask(task);

        Assert.assertEquals(tasksList.size(), 2);
    }

    @Test(description = "addTask должен выбрасывать IllegalArgumentException при null")
    public void testAddTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> tasksList.addTask(null));
    }

    @Test(description = "delTask должен удалять задачу из списка")
    public void testDelTask() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);
        tasksList.delTask(task);

        Assert.assertFalse(tasksList.contains(task));
        Assert.assertEquals(tasksList.size(), 0);
    }

    @Test(description = "delTask должен не падать, если задача не найдена")
    public void testDelTaskNotFound() {
        Task task = new Task("Купить хлеб");
        tasksList.delTask(task); // не должно кидать исключение

        Assert.assertEquals(tasksList.size(), 0);
    }

    @Test(description = "delTask должен выбрасывать IllegalArgumentException при null")
    public void testDelTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> tasksList.delTask(null));
    }

    @Test(description = "fromStore должен корректно читать задачи из файла")
    public void testFromStore() throws IOException {
        File tempFile = new File("tasks_test_" + System.currentTimeMillis() + ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile.toPath(), StandardCharsets.UTF_8)) {
            writer.write("abc123;Купить хлеб;ivan;2025-04-05T10:00:00;2025-04-05T10:00:00;true");
            writer.newLine();
            writer.write("def456;Проверить почту;alex;2025-04-05T11:00:00;2025-04-05T11:00:00;false");
        }

        tasksList.fromStore(tempFile.getAbsolutePath());

        Assert.assertEquals(tasksList.size(), 2);
        Task expectedTask = Task.fromStore("abc123;Купить хлеб;ivan;2025-04-05T10:00:00;2025-04-05T10:00:00;true");
        Assert.assertTrue(tasksList.contains(expectedTask));
    }

    @Test(description = "fromStore должен выбрасывать IOException при несуществующем файле")
    public void testFromStoreFileNotFound() {
        assertThrows(IOException.class, () -> tasksList.fromStore("nonexistent_file.txt"));
    }

    @Test(description = "toStore должен корректно записывать задачи в файл")
    public void testToStore() throws IOException {
        File tempFile = new File("tasks_test_" + System.currentTimeMillis() + ".txt");
        tempFile.deleteOnExit();

        Task task1 = new Task();
        task1.setName("Купить хлеб");
        task1.setOwner("ivan");

        Task task2 = new Task();
        task2.setName("Проверить почту");
        task2.setOwner("alex");

        tasksList.addTask(task1);
        tasksList.addTask(task2);

        tasksList.toStore(tempFile.getAbsolutePath());

        List<String> lines = Files.readAllLines(tempFile.toPath(), StandardCharsets.UTF_8);
        Assert.assertEquals(lines.size(), 2);
        Assert.assertTrue(lines.get(0).contains("Купить хлеб"));
        Assert.assertTrue(lines.get(1).contains("Проверить почту"));
    }

    @Test(description = "toStore должен выбрасывать RuntimeException при ошибке записи")
    public void testToStoreIOException() {
        assertThrows(RuntimeException.class, () -> {
            tasksList.toStore("/root/forbidden/file.txt");
        });
    }

    @Test(description = "getTasks должен возвращать неизменяемый список")
    public void testGetTasksUnmodifiable() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);

        List<Task> external = tasksList.getTasks();

        assertThrows(UnsupportedOperationException.class, () -> external.add(new Task("Новая задача")));
    }

    @Test(description = "getTasks должен возвращать неприменимый список (нельзя менять)")
    public void testGetTasksIsUnmodifiable() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);

        List<Task> external = tasksList.getTasks();

        // Проверяем, что вызовы изменений бросают исключение
        try {
            external.add(new Task("Новая задача"));
            Assert.fail("Ожидается UnsupportedOperationException при вызове add()");
        } catch (UnsupportedOperationException e) {
            // ОК — ожидаемое поведение
        }

        try {
            external.remove(0);
            Assert.fail("Ожидается UnsupportedOperationException при вызове remove()");
        } catch (UnsupportedOperationException e) {
            // ОК
        }

        try {
            external.clear();
            Assert.fail("Ожидается UnsupportedOperationException при вызове clear()");
        } catch (UnsupportedOperationException e) {
            // ОК
        }

        try {
            external.set(0, new Task("Изменённая задача"));
            Assert.fail("Ожидается UnsupportedOperationException при вызове set()");
        } catch (UnsupportedOperationException e) {
            // ОК
        }
    }

    @Test(description = "toString должен возвращать строку с задачами, разделёнными переносом строки")
    public void testToString() {
        Task task1 = new Task("Купить хлеб");
        Task task2 = new Task("Проверить почту");
        tasksList.addTask(task1);
        tasksList.addTask(task2);

        String result = tasksList.toString();

        Assert.assertTrue(result.contains("Купить хлеб"));
        Assert.assertTrue(result.contains("Проверить почту"));
        Assert.assertTrue(result.contains(System.lineSeparator()));
    }

    @Test(description = "isEmpty должен возвращать true, если список пуст")
    public void testIsEmpty() {
        Assert.assertTrue(tasksList.isEmpty());
    }

    @Test(description = "isEmpty должен возвращать false, если есть задачи")
    public void testIsEmptyWithTasks() {
        tasksList.addTask(new Task("Купить хлеб"));
        Assert.assertFalse(tasksList.isEmpty());
    }

    @Test(description = "getTaskById должен возвращать null, если задача не найдена")
    public void testGetTaskByIdNotFound() {
        Task task = tasksList.getTaskById("nonexistent");
        Assert.assertNull(task);
    }

    @Test(description = "getTaskById должен выбрасывать NullPointerException при null id")
    public void testGetTaskByIdNull() {
        assertThrows(NullPointerException.class, () -> tasksList.getTaskById(null));
    }

    @Test(description = "equals должен сравнивать по содержимому списка задач")
    public void testEquals() {
        TasksList list1 = new TasksList();
        TasksList list2 = new TasksList();

        Task task = new Task("Купить хлеб");
        list1.addTask(task);
        list2.addTask(task);

        Assert.assertTrue(list1.equals(list2));
        Assert.assertTrue(list2.equals(list1));
    }

    @Test(description = "hashCode должен быть одинаковым для равных объектов")
    public void testHashCode() {
        TasksList list1 = new TasksList();
        TasksList list2 = new TasksList();

        Task task = new Task("Купить хлеб");
        list1.addTask(task);
        list2.addTask(task);

        Assert.assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test(description = "size должен возвращать количество задач в списке")
    public void testSize() {
        Task task = new Task("Купить хлеб");
        tasksList.addTask(task);
        Assert.assertEquals(tasksList.size(), 1);
    }
}