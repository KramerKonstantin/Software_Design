package drawing;

import javafx.scene.canvas.GraphicsContext;

public class DrawingApiFx implements DrawingApi {
    private final GraphicsContext gc;
    private final long width;
    private final long height;

    public DrawingApiFx(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.height = height;
        this.width = width;
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
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }
}
