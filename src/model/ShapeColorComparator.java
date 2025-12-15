package model;

import java.util.Comparator;

public class ShapeColorComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape a, Shape b) {
        String ca = a.getShapeColor() == null ? "" : a.getShapeColor();
        String cb = b.getShapeColor() == null ? "" : b.getShapeColor();
        return ca.compareToIgnoreCase(cb);
    }
}
