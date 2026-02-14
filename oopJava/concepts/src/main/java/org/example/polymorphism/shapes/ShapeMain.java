package main.java.org.example.polymorphism.shapes;

public class ShapeMain {
    public static void main(String[] args) {
        IShapeCalculator shape_1 = new Circle(4);

        // уровень абстракции 4
        // пользователя интересует площадь фигуры, операции по определению реализации и метода счета скрыты
        System.out.println(shape_1.getSquare());
        
        IShapeCalculator shape_2 = new Square(4);
        // фигура другого типа, но пользователя это не интересует, требуется площадь
        System.out.println(shape_2.getSquare());
        
        
        // вызов методов, не объявленных в интерфейсе
        // 
        // требует приведения типов 
        // => нарушение границы абстракции здесь, в клиентском коде
        IShapeCalculator shape_3 = new Square(4);
        System.out.println(((Square) shape_3).getPerimeter());

        // привели круг к квадрату - логическая ошибка
        IShapeCalculator shape_4 = new Circle(4);
        System.out.println(((Square) shape_4).getPerimeter());
        
        // нет реализации расчета периметра окружности
//        System.out.println(shape_4).getPerimeter();
        
    }
}
