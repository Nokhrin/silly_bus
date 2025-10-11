package pop.lesson09;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сущность "Список задач"
 * Является коллекцией ссылок на задачи
 * @see Task
 * ---
 * Существует в единственном экземпляре
 * ---
 * Поддерживает операции
 * - создание
 * @see TasksList#TasksList(List)
 * - добавление задачи в список
 * @see TasksList#addTask(Task)
 * - удаление задачи из списка
 * @see TasksList#delTask(Task)
 * - получение списка задач в виде Java коллекции
 * @see TasksList#getTasks()
 * - проверка вхождения задачи в список задач
 * @see TasksList#contains
 * - получение текстового представления списка задач
 * @see TasksList#toString()
 * - запись списка задач в файл
 * @see TasksList#toStore(String)
 * - чтение задач из файла
 * @see TasksList#fromStore(String)
 * - получение количества задач в списке
 * @see TasksList#size()
 * - получение состояния списка - пустой/не пустой
 * @see TasksList#isEmpty()
 */
public class TasksList {
    /**
     * Список задач
     *  в единственном экземпляре
     *  доступен глобально - из всех модулей проекта
     *  предоставляет извлечение объектов
     *  инкапсулирован - не позволяет вызвать конструктор с помощью оператора new
     */

    // хранилище объектов
    private final List<Task> tasks;

    /**
     * Конструктор по умолчанию — создаёт пустой список
     */
    public TasksList() {
        this.tasks = new ArrayList<>();
    }

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
