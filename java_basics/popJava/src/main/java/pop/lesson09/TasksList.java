package pop.lesson09;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TasksList {
    private List<Task> tasks;

    /**
     * Создаю объект списка задачи из List задач
     */
    public TasksList(List<Task> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Вместо списка задач передан null");
        }
        this.tasks = new ArrayList<>(tasks);
    }
    /**
     * Добавляю задачу в список
     * @param task
     */
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Вместо объекта задачи передан null");
        }
        this.tasks.add(task);
    }

    public void delTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Вместо объекта задачи передан null");
        }
        this.tasks.remove(task);
    }

    /**
     * Читаю задачи из файла
     * @param filePath путь к файлу с задачами
     * @return экземпляр TasksList - список задач
     * @throws IOException - файл не найден или ошибка чтения файла
     */
    public static TasksList fromStore(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("Вместо пути к файлу передан null");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            var tasksList = new TasksList(new ArrayList<>());
            String line;
            while ((line = br.readLine()) != null) {
                var task = Task.fromStore(line);
                tasksList.tasks.add(task);
            }
            return tasksList;
        } catch (FileNotFoundException e) {
            throw new IOException(String.format("Файл %s не найден\n%s", filePath, e));
        } catch (IOException e) {
            throw new IOException(String.format("Ошибка чтения файла %s\n%s", filePath, e));
        }

    }

    /**
     * Записываю задачи в файл
     * @param filePath путь к файлу с задачами
     */
    public void toStore(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("Вместо пути к файлу передан null");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : this.tasks) {
                writer.write(task.toStore());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + filePath, e);
        }
    }

    /**
     * Создаю строковое представление списка задач
     * @return строка - задачи, разделенные переносом строки
     */
    @Override
    public String toString() {
        return this.tasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Проверяю наличие задачи в списке задач
     */
    public boolean contains(Task task) {
        return this.tasks.contains(task);
    }

    /**
     * Определяю, пуст ли список задач
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Возвращаю количество задач, входящих в список
     */
    public int size() {
        return this.tasks.size();
    }

    // геттер
    /**
     * Возвращаю список задач
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }


    /**
     * Сравниваю содержимое экземпляра списка задач при вызове equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        TasksList listToCheck = (TasksList) obj;
        return Objects.equals(this, listToCheck);
    }

    /**
     * Определяю хэш-сумму экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.tasks);
    }
}
