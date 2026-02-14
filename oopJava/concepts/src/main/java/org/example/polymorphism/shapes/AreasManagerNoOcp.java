package main.java.org.example.polymorphism.shapes;

import java.util.List;
import java.util.Objects;

public class AreasManagerNoOcp {
    public static double sumAreas(List<Objects> shapes) {
        double sum = 0;

        for (Object shape : shapes) {
            if (shape instanceof Circle) {
                sum += Math.PI * ((Circle) shape).radius() * ((Circle) shape).radius();
            } else if (shape instanceof Square) {
                sum += ((Square) shape).sideLength() * ((Square) shape).sideLength();
            }
        }
        
        return sum;
    }

}
