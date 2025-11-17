package MultipleInheritance.AbstractClasses;

public abstract class Task {
    // поля
    String title;
    boolean isCompleted;

    // конструктор
    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    // абстрактный метод
    abstract void execute();

    // конкретный метод
    void markCompleted() {
        this.isCompleted = true;
    }

    // геттеры
    public String getTitle() { return this.title; }
    public boolean isCompleted() { return this.isCompleted; }
}
