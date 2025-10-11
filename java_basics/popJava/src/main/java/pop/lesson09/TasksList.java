package pop.lesson09;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс-коллекция задач, реализующий паттерн "Одиночка" (Singleton).
 * <p>
 * Является глобальным хранилищем задач. Обеспечивает:
 * <ul>
 *   <li>Единственный экземпляр в приложении</li>
 *   <li>Глобальный доступ к списку задач</li>
 *   <li>Инкапсуляцию данных</li>
 *   <li>Возможность сохранения и загрузки из файла</li>
 * </ul>
 * <p>
 * Поддерживает операции:
 * <ul>
 *   <li>Добавление задачи</li>
 *   <li>Удаление задачи</li>
 *   <li>Поиск по ID или по объекту</li>
 *   <li>Сохранение/восстановление из файла</li>
 *   <li>Получение списка в виде неизменяемого списка</li>
 * </ul>
 * <p>
 * <b>Важно:</b> Экземпляр доступен только через статический метод {@link #getInstance()}.
 * Прямое создание через конструкторы запрещено.
 *
 * @see Task
 * @author nohal
 * @version 1.0
 */
public class TasksList {
    // единственный экземпляр списка
    private static TasksList instance;
    // хранилище объектов
    private final List<Task> tasks;

    /**
     * Приватный конструктор - не получится создать экземпляр с помощью new
     */
    private TasksList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Возвращаю экземпляр списка задач
     * Единственная точка доступа к списку задач
     */
    public static TasksList getInstance() {
        if (instance == null) {
            instance = new TasksList();
        }
        return instance;
    }

    /**
     * Создаю объект списка задачи из List задач
     */
    public TasksList(List<Task> tasks) {
        Objects.requireNonNull(tasks, "Список задач не может быть null");

        this.tasks = new ArrayList<>(tasks);
    }
    /**
     * Добавляю задачу в список
     * @param task
     */
    public void addTask(Task task) {
        Objects.requireNonNull(task, "Задача не может быть null");

        this.tasks.add(task);
    }

    public boolean delTask(Task task) {
        Objects.requireNonNull(task, "Задача не может быть null");
        return this.tasks.remove(task);
    }

    /**
     * Очистить список задач
     */
    public void clear() {
        this.tasks.clear();
    }

    /**
     * Читаю задачи из файла
     * @param filePath путь к файлу с задачами
     * @throws IOException - файл не найден или ошибка чтения файла
     */
    public void fromStore(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("Вместо пути к файлу передан null");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                var task = Task.fromStore(line);
                this.tasks.add(task);
            }
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
     * Возвращаю задачу из списка по id задачи
     */
    public Task getTaskById(String id) {
        if (id == null) {
            throw new NullPointerException("Получен null в качестве значения id");
        }
        for (Task task : this.tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
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
        return Objects.equals(this.tasks, listToCheck.tasks);
    }

    /**
     * Определяю хэш-сумму экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.tasks);
    }
}
