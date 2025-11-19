package MultipleInheritance.Interfaces;
/**
Интерфейс в роли суперкласса
 */
interface A {
    default void execute() {
        System.out.println("интерфейс A: default метод");
    }
}

interface B {
    default void execute() {
        System.out.println("интерфейс B: default метод");
    }
}

/**
Польза Override - проверка наличия метода в суперклассе или интерфейсе в compile time
 */
//class C0 implements A, B {
//    @Override
//    public void execute() {
//        System.out.println("корректно переопределил метод");
//    }
//    @Override
//    public void execut() {
//        System.out.println("ошибся в наименовании переопределяемого метода");
//    }
//}
/*
javac src/main/java/MultipleInheritance/Interfaces/InterfaceDefaultCheck.java
src/main/java/MultipleInheritance/Interfaces/InterfaceDefaultCheck.java:22: error: method does not override or implement a method from a supertype
    @Override
    ^
1 error
 */

class C1 implements A {
}

class C2 implements B {
}

//class C3 implements A, B {
//}
/*
javac src/main/java/MultipleInheritance/Interfaces/InterfaceDefaultCheck.java
src/main/java/MultipleInheritance/Interfaces/InterfaceDefaultCheck.java:23: error: types A and B are incompatible;
class C3 implements A, B {
^
  class C3 inherits unrelated defaults for execute() from types A and B
1 error
 */

class C4 implements A, B {
    // обращение к интерфейсу как при переопределении метода класса
    @Override
    public void execute() {
        // ссылаюсь на определенный метод определенного суперкласса
        A.super.execute();
    }
}

class C5 implements A, B {
    public void execute() {
        System.out.println("C5 - своя реализация execute");
    }
}

public class InterfaceDefaultCheck {
    public static void main(String[] args) {
        C1 c1 = new C1();
        c1.execute(); // интерфейс A: default метод

        C2 c2 = new C2();
        c2.execute(); // интерфейс B: default метод

        C4 c4 = new C4();
        c4.execute(); // интерфейс A: default метод

        C5 c5 = new C5();
        c5.execute(); // C5 - своя реализация execute
    }
}
