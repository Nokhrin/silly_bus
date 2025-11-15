package MultipleInheritance.Interfaces;

public interface DefaultB {
    default void method() {
        System.out.println("B - метод по умолчанию");
    }
}
