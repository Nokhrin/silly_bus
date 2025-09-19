package pop.lesson09;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TasksList {
    private List<Task> tasks;

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void delTask(Task task) {
        this.tasks.remove(task);
    }

    public static TasksList fromStore(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            var tasksList = new TasksList();
            tasksList.tasks = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                var task = Task.fromStore(line);
                tasksList.tasks.add(task);
            }
            return tasksList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Файл %s не найден\n%s", filePath, e));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Ошибка чтения файла %s\n%s", filePath, e));
        }

    }

    public void toStore(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : this.tasks) {
                writer.write(task.getStrRepr());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи файла: " + filePath, e);
        }
    }

    /**
     * Создаю строковое представление списка задач
     * @return
     */
    private String getStrRepr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            sb.append(this.tasks.get(i));
            if (i < this.tasks.size() - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
