package model;

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Rectangle " + shapeColor);
    }

    @Override
    public String toString() {
        return "Rectangle{" + shapeColor + ", " + String.format("%.2f", calcArea()) + "}";
    }
}
