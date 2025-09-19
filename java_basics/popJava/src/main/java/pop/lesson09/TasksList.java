package pop.lesson09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TasksList {
    private List<Task> tasks;

    public static TasksList fromStore(Path filepath) {
        try {
            var lines = Files.readAllLines(filepath);
            var tasksList = new TasksList();
            tasksList.tasks = new ArrayList<>();
            for (String line : lines) {
                var task = Task.fromStore(line);
                tasksList.tasks.add(task);
            }
            return tasksList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
