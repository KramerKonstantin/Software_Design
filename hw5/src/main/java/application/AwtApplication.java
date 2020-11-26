package application;

import drawing.DrawingApiAwt;
import graph.Graph;
import graph.TypeGraph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

public class AwtApplication  implements GraphApplication {
    private List<Shape> shapes;

    @Override
    public void startApplication() {
        int width = 600;
        int height = 600;

        DrawingApiAwt drawingApi = new DrawingApiAwt(width, height);
        shapes = drawingApi.getShapes();

        Graph graph = TypeGraph.createGraph(drawingApi);
        graph.getGraph();
        graph.drawGraph();

        Frame frame = new AwtFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    class AwtFrame extends Frame {
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(Color.green);
            for (Shape shape : shapes) {
                g2d.draw(shape);
            }
        }
    }
}