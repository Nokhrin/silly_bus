package pop.lesson09;

import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FileOperationsTest {

    private static final String TEST_FILE_PATH = "test-file.txt";
    private static final List<String> EXPECTED_CONTENT = Arrays.asList("Строка 1","Строка 2","Строка 3","Строка 4");

    @Test(description = "Проверка записи файла на диск")
    public void testWriteFileExistence() {
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

    @Test(description = "Проверка содержимого файла")
    public void testReadFileContent() {
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

}