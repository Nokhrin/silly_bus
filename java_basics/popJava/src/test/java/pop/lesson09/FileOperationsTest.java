package pop.lesson09;

import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static pop.lesson09.TaskTracker.readFromFile;
import static pop.lesson09.TaskTracker.writeToFile;

public class FileOperationsTest {

    private static final String TEST_FILE_PATH = "test-file.txt";
    private static final List<String> EXPECTED_CONTENT = Arrays.asList("Строка 1","Строка 2","Строка 3","Строка 4");

    @Test(description = "Проверка записи файла на диск")
    public void testWriteToFileExistence() {
        // Убедимся, что файл существует перед тестом (очистим, если есть)
        File actualFile = new File(TEST_FILE_PATH);
        if (actualFile.exists()) {
            actualFile.delete();
        }

        // Реализация записи файла
        TaskTracker.writeToFile(Arrays.asList("привет", "файл"), TEST_FILE_PATH);
        assertTrue(actualFile.exists(), "Файл не был создан на диске");
        assertTrue(actualFile.isFile(), "Указанный путь не является файлом");

        // Удаляем тестовый файл после тестов
        if (actualFile.exists()) {
            actualFile.delete();
        }
    }

    @Test()
    public void testWriteToFileContent() {
        List<String> expected = Arrays.asList("Строка 1","Строка 2","Строка 3","Строка 4");

        writeToFile(expected, TEST_FILE_PATH);

        List<String> actual = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                actual.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Файл %s не найден\n%s", TEST_FILE_PATH, e));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Ошибка чтения файла %s\n%s", TEST_FILE_PATH, e));
        }

        assertEquals(actual, expected);
    }

    @Test(description = "Проверка содержимого файла")
    public void testReadFromFileContent() {
        File expectedFile = new File(TEST_FILE_PATH);
        // Убедимся, что файл существует перед тестом (очистим, если есть)
        if (expectedFile.exists()) {
            expectedFile.delete();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            for (String line : EXPECTED_CONTENT) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + TEST_FILE_PATH, e);
        }

        List<String> actual = TaskTracker.readFromFile(TEST_FILE_PATH);
        assertEquals(actual, EXPECTED_CONTENT);

        // Удаляем тестовый файл после тестов
        if (expectedFile.exists()) {
            expectedFile.delete();
        }
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testReadFromFileNotExist() {
        String fileName = "TaskTrackerData.dump";
        readFromFile(fileName);
        throw new RuntimeException();
    }



}