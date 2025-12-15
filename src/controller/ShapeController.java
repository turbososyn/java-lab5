package controller;

import model.Shape;
import java.util.Arrays;
import java.util.Comparator;

public class ShapeController {
    private final Shape[] shapes;

    public ShapeController(Shape[] shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    public double totalArea() {
        double sum = 0;
        for (Shape s : shapes) sum += s.calcArea();
        return sum;
    }

    public double totalAreaByClass(Class<? extends Shape> cls) {
        double sum = 0;
        for (Shape s : shapes) if (cls.isInstance(s)) sum += s.calcArea();
        return sum;
    }

    public Shape[] sortedCopy(Comparator<Shape> comparator) {
        Shape[] copy = Arrays.copyOf(shapes, shapes.length);
        Arrays.sort(copy, comparator);
        return copy;
    }
}
