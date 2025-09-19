package pop.lesson09;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String owner = System.getProperty("user.name");
    private LocalDateTime create = LocalDateTime.now();
    private LocalDateTime modify = LocalDateTime.now();
    private boolean done = false;

    public Task(String name) {
        this.name = name;
    }

    private Task(String id, String name, String owner, LocalDateTime create, LocalDateTime modify, boolean done) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.create = create;
        this.modify = modify;
        this.done = done;
    }

    public String toStore() {
        return this.id + ";" + this.name + ";" + this.owner + ";" + this.create + ";" + this.modify + ";" + this.done;
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
     * @return
     */
    public String getStrRepr() {
        return "Печатаю Задачу " + this.id + ";" + this.name + ";" + this.owner + ";" + this.create + ";" + this.modify + ";" + this.done;
    }

    public void setName(String name) {
        this.name = name;
        this.modify = LocalDateTime.now();
    }

    public void setOwner(String owner) {
        this.owner = owner;
        this.modify = LocalDateTime.now();
    }

    public void setDone(boolean done) {
        this.done = done;
        this.modify = LocalDateTime.now();
    }
}
