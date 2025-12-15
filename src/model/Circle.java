package model;

public class Circle extends Shape {
    private final double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calcArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Circle " + shapeColor);
    }

    @Override
    public String toString() {
        return "Circle{" + shapeColor + ", " + String.format("%.2f", calcArea()) + "}";
    }
}
