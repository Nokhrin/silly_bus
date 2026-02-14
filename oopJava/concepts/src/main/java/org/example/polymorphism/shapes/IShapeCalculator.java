package main.java.org.example.polymorphism.shapes;

public interface IShapeCalculator {
    double getSquare();
}

record Square(double sideLength) implements IShapeCalculator {
    @Override
    public double getSquare() {
        return sideLength * sideLength;
    }
}

record Circle(double radius) implements IShapeCalculator {
    @Override
    public double getSquare() {
        return Math.PI *radius*radius;
    }
}
