package MultipleInheritance.Classes;

public class Main {
    public static void main(String[] args) {
        D1 d1 = new D1();
        d1.method(); // B

        D2 d2 = new D2();
        d2.method(); // C

        // запрещено в Java
//        D3 d3 = new D3();
//        d3.method(); // B или C ?

        // пробую наследовать от базового класса Object и от A
        // запрещено
//        D4 d4 = D4();
    }
}
