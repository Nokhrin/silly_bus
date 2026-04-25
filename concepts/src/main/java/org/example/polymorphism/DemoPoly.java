package org.example.polymorphism;

class A {
    // целые числа
    static String methodA(int n) {
        return "methodA: " + n;
    }
}

class B {
    // только неотрицательные целые числа
    static String methodB(int n) {
        if (n > -1) {
            return "methodB: " + n;
        }
        throw new IllegalArgumentException("число должно быть неотрицательным");
    }
}

public class DemoPoly {


    public static void main(String[] args) {
        // данные, обрабатываемые в существующем классе
        String data;
        
        // api - реализация 1
        data = A.methodA(2);

        // клиент - вызывает api
        System.out.println(data);

        // api - реализация 2
        data = B.methodB(2);
        
        // клиент - вызывает api - логика отличается, сигнатура сохранена
        System.out.println(data);
    }

}
