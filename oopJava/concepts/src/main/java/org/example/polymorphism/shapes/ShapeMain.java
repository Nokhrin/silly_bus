package main.java.org.example.polymorphism.shapes;

public class ShapeMain {
    public static void main(String[] args) {
        IShapeCalculator shape_1 = new Circle(4);

        // пользователя интересует площадь фигуры, операции по определению реализации и метода счета скрыты
        System.out.println(shape_1.getSquare());
        
        IShapeCalculator shape_2 = new Square(4);
        // фигура другого типа, но пользователя это не интересует, требуется площадь
        System.out.println(shape_2.getSquare());
        
        
    }
}
