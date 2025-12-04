package DemoTyping;

public class MainBroken {
    public static void main(String[] args) {
        A a = new A();
//        A a = new B();  // строка, вызывающая ошибку компиляции

        a.sayHello();
        a.sayHello();
    }
}