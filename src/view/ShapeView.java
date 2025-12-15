package view;

import model.Shape;

public class ShapeView {
    public void printTitle(String title) {
        System.out.println("\n" + title);
    }

    public void printShapes(Shape[] shapes) {
        for (Shape s : shapes) System.out.println(s);
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }
}
