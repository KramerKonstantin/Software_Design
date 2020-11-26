package graph;

import drawing.DrawingApi;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Graph {
    private final DrawingApi drawingApi;
    private final Map<Integer, Map.Entry<Double, Double>> vertexCoordinates = new HashMap<>();
    private double radius;
    private double centerX;
    private double centerY;
    private double corner;
    private double deltaCorner;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void getGraph();

    public abstract void drawGraph();

    protected void init(int countVertex) {
        long width = drawingApi.getDrawingAreaWidth();
        long height = drawingApi.getDrawingAreaHeight();

        radius = ((double) Math.min(width, height)) / 2.0 - 20;
        centerX = (double) width / 2;
        centerY = (double) height / 2;
        deltaCorner = 2 * Math.PI / countVertex;
    }

    private void drawVertex(int v) {
        if (!vertexCoordinates.containsKey(v)) {
            double x = centerX + radius * Math.cos(corner);
            double y = centerY + radius * Math.sin(corner);
            double r = 10;

            drawingApi.drawCircle(x, y, r);
            vertexCoordinates.put(v, new AbstractMap.SimpleEntry<>(x, y));
            corner += deltaCorner;
        }
    }

    private void connectVertex(int v, int u) {
        Map.Entry<Double, Double> start = vertexCoordinates.get(v);
        Map.Entry<Double, Double> end = vertexCoordinates.get(u);
        drawingApi.drawLine(start.getKey(), start.getValue(), end.getKey(), end.getValue());
    }

    protected void drawEdge(int v, int u) {
        drawVertex(v);
        drawVertex(u);
        connectVertex(v, u);
    }
}
