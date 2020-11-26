package graph;

import drawing.DrawingApi;

public class TypeGraph {
    public static GraphType graph;

    public static Graph createGraph(DrawingApi drawingApi) {
        return switch (graph) {
            case LIST -> new ListGraph(drawingApi);
            case MATRIX -> new MatrixGraph(drawingApi);
        };
    }
}
