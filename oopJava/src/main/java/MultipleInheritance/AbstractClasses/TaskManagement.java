package MultipleInheritance.AbstractClasses;

public class TaskManagement {
    // так не сработает - Java требует реализовать абстрактный метод
    // Class 'Anonymous class derived from Task' must implement abstract method 'execute()' in 'Task'
//    public static void main(String[] args) {
//        Task task = new Task("задача") {
//        };
//    }

    public static void main(String[] args) {
        Task task = new Task("задача") {
            @Override
            void execute() {
                System.out.println("реализация в main: выполняю задачу");
            }
        };

        task.execute(); // реализация в main: выполняю задачу
    }
}
