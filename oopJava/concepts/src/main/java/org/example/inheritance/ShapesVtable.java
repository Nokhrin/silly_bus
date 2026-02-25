package org.example.inheritance;


/**
 *     родитель: Shape, метод area()
 *     v                       v
 * наследник: Circle         наследник: Square
 * метод area()              метод area()
 */
abstract class Shape {
    abstract double area();
}


class Circle extends Shape {
    private double r;

    public Circle(double r) {
        this.r = r;
    }

    @Override
    double area() {
        return Math.PI * r * r;
    }
}

class Square extends Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    double area() {
        return side * side;
    }
}



public class ShapesVtable {
    public static void main(String[] args) {
        
        // объекты circle и square типа Shape => в compile нельзя определить, какой метод будет в runtime
        
        Shape circle = new Circle(5);
        //vtable: индекс в массиве, ссылка на память с реализацией
        //[0] -> Circle.area@66a29884
        
        
        Shape square = new Square(4);
        
        
        System.out.println(circle); // адрес в памяти
        System.out.println(circle.getClass().getSimpleName()); // circle - есть Shape
        System.out.println(circle.area()); // виртуальный метод
        //org.example.inheritance.Circle@1b2c6ec2
        //Circle
        //78.53981633974483

        System.out.println(square); // адрес в памяти
        System.out.println(square.getClass().getSimpleName()); // square - есть Shape
        System.out.println(square.area()); // виртуальный метод
        //org.example.inheritance.Square@17a7cec2
        //Square
        //16.0
    }
}

