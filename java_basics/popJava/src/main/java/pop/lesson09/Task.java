package pop.lesson09;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Сущность "Задача"
 * Минимальная единица, которой манипулирует трекер
 * @see Task
 * ---
 * Задача поддерживает операции
 * - создание
 * @see Task#Task(String, String, String, LocalDateTime, LocalDateTime, boolean)
 * - обновление - для изменения доступны
 *  - заголовок задачи
 *  @see Task#setName(String)
 *  - владелец задачи
 *  @see Task#setOwner(String)
 *  - статуч задачи
 *  @see Task#setDone(boolean)
 * - печать текстового представления
 * @see Task#toString()
 * ---
 * Задача всегда включена в список задач
 * список задач - хранилище ссылок на задачи
 * @see TasksList
 * удаление задачи из списка задач есть удаление ссылки на задачу
 *  и, как следствие, удаление экземпляра задачи
 */
public class Task {
    private String id; // не должен изменяться после создания
    private String name; 
    private String owner = System.getProperty("user.name");
    private final LocalDateTime create; // не должен изменяться после создания
    private LocalDateTime modify = LocalDateTime.now();
    private boolean done = false;

    /**
     * Конструктор - позволяет задать значение final полям
     * Значения id, время создания генерируются при вызове оператора new
     */
    public Task() {
        this.id = UUID.randomUUID().toString();
        this.create = LocalDateTime.now();
        this.name = "Задача " + this.id;
        this.modify = this.create;
    }

    /**
     * Конструктор - для импорта из файла - требует значения для всех полей Task
     * @param id
     * @param name
     * @param owner
     * @param create
     * @param modify
     * @param done
     */
    public Task(String id, String name, String owner, LocalDateTime create, LocalDateTime modify, boolean done) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.create = create;
        this.modify = modify;
        this.done = done;
    }

    public Task(String name, String owner) {
        this.id = UUID.randomUUID().toString();
        this.create = LocalDateTime.now();
        this.name = name;
        this.owner = owner;
        this.modify = this.create;
        this.done = false;
    }

    public Task(String name) {
        this.id = UUID.randomUUID().toString();
        this.create = LocalDateTime.now();
        this.name = name;
        this.owner = System.getProperty("user.name");;
        this.modify = this.create;
        this.done = false;
    }

    /**
     * Создаю текстовое представление задачи для записи в файл
     * @return  значения полей, разделенный символом ";"
     */
    public String toStore() {
        return String.join(";", this.id,  this.name,  this.owner,  this.create.toString(),  this.modify.toString(),  Boolean.toString(this.done));
    }

    public static Task fromStore(String taskAsString) {
        var fields = taskAsString.split(";");
        if (fields.length != 6) {
            throw new Error("Неверное значение параметра: " + taskAsString);
        }
        var id = fields[0];
        var name = fields[1];
        var owner = fields[2];
        var create = LocalDateTime.parse(fields[3]);
        var modify = LocalDateTime.parse(fields[4]);
        var done = Boolean.parseBoolean(fields[5]);
        return new Task(id, name, owner, create, modify, done);
    }

    /**
     * Создаю строковое представление задачи
     * @return строка со значениями всех атрибутов
     */
    public String toString() {
        return "\nid=" + this.id +
                "\nname=" + this.name +
                "\nowner=" + this.owner +
                "\ncreate=" + this.create +
                "\nmodify=" + this.modify +
                "\ndone=" + this.done;
    }

    // getters
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getOwner() {
        return this.owner;
    }
    public LocalDateTime getCreate() {
        return this.create;
    }
    public LocalDateTime getModify() {
        return this.modify;
    }
    public boolean isDone() {
        return this.done;
    }

    // setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым или null");
        }
        this.name = name;
        this.modify = LocalDateTime.now();
    }

    public void setOwner(String owner) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя владельца не может быть пустым или null");
        }
        this.owner = owner;
        this.modify = LocalDateTime.now();
    }

    public void setDone(boolean done) {
        this.done = done;
        this.modify = LocalDateTime.now();
    }

    /**
     * Определяю содержимое для метода сравнения объектов Задач
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id.equals(task.id);
    }

    /**
     * Определяю, по какому значению получать хэш-сумму экземпляра
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

