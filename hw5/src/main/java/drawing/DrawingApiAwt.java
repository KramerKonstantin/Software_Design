package drawing;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingApiAwt implements DrawingApi {
    List<Shape> shapes;
    private final int width;
    private final int height;

    public DrawingApiAwt(int width, int height) {
        this.shapes = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    @Override
    public long getDrawingAreaWidth() {
        return width;
    }

    @Override
    public long getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        shapes.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        shapes.add(new Line2D.Double(x1, y1, x2, y2));
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}
