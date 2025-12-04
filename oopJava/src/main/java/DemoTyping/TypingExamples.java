package DemoTyping;

public class TypingExamples {
    public static void main(String[] args) {
        // проверка типов переменных
        int a = 1;
        String b = "2";

        // слабая типизация ?
        //  значение переменной a будет приведено к строке,
        //  приведение неявно инициирует метод println
        //  оператор + выполнит конкатенацию строк
        System.out.println(a + b); // "12"

        // строгая типизация ?
        // если не привести b к int
        //  или не привести c и a к String
        //  произойдет ошибка компиляции
        //
        // src/main/java/DemoTyping/TypingExamples.java:18: error: incompatible types: String cannot be converted to int
        //        int c = a + b; //
        //                  ^
        //1 error
        // int c = a + b; //
        // System.out.println(c);

        // проверка типа выражения - ожидается экземпляр int
        int c = a + Integer.parseInt(b);
        System.out.println(c); // 3

        // переопределение переменной запрещено
        // src/main/java/DemoTyping/TypingExamples.java:30: error: variable c is already defined in method main(String[])
        //        String c = String.valueOf(a) + b;
        //               ^
        //1 error
        // String c = String.valueOf(a) + b;

        // проверка типа выражения - ожидается экземпляр String
        String d = String.valueOf(a) + b;
        System.out.println(d); // "12"
    }
}
