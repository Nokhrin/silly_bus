package main.java.org.example.polymorphism.shapes;

// уровень абстракции 2
public interface IShapeCalculator {
    double getSquare();
    
    // контракт на периметр не предоставляется
//    double getPerimeter();
}


// уровень абстракции 3
record Square(double sideLength) implements IShapeCalculator {
    @Override
    public double getSquare() {
        return sideLength * sideLength;
    }
    
    public double getPerimeter() {
        return 4*sideLength;
    }
}

record Circle(double radius) implements IShapeCalculator {
    @Override
    public double getSquare() {
        return Math.PI *radius*radius;
    }
}
