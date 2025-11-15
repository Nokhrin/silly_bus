package MultipleInheritance.Interfaces;

public class Main {
    public static void main(String[] args) {
        Deposit deposit = new Deposit();
        deposit.perform(); // выполняю зачисление

        DOverride dOverride = new DOverride();
        dOverride.method(); // переопеределенный метод

        DSuper dSuper = new DSuper();
        dSuper.method(); // A - метод по умолчанию
    }
}
