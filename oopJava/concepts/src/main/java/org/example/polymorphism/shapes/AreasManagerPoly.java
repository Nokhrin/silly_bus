package main.java.org.example.polymorphism.shapes;

import java.util.List;

public class AreasManagerPoly {
    public static double sumAreas(List<IShapeCalculator> shapes) {
        double sum = 0;
        for (IShapeCalculator calc : shapes) {
            sum += calc.getSquare();
        }
        return sum;
    }

}
