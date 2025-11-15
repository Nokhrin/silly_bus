package MultipleInheritance.Interfaces;

public interface DefaultA {
    default void method() {
        System.out.println("A - метод по умолчанию");
    }
}
