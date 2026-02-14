package main.java.org.example.polymorphism.shapes;

import java.util.List;
import java.util.Objects;

public class AreasManagerNoPoly {
    public static double sumAreas(List<Objects> shapes) {
        double sum = 0;

        for (Object shape : shapes) {
            // фигура 1
            if (shape instanceof Circle) {
                sum += Math.PI * ((Circle) shape).radius() * ((Circle) shape).radius();
            // фигура 1
            } else if (shape instanceof Square) {
                sum += ((Square) shape).sideLength() * ((Square) shape).sideLength();
            }
            // фигура N
            // else if ...
        }
        
        return sum;
    }

}
