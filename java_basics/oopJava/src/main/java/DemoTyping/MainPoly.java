package DemoTyping;

public class MainPoly {
    public static void main(String[] args) {
        C c;

        c = new C();
        c.sayHello();
        C.sayStatic();

        c = new D();
        c.sayHello();
        D.sayStatic();  // sayStatic унаследован, не переопределен
    }
}