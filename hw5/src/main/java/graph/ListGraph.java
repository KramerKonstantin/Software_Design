package graph;

import drawing.DrawingApi;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ListGraph extends Graph {
    private int countOfVertex;
    private int[][] listOfEdge;

    public ListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void getGraph() {
        Scanner in = new Scanner(System.in);

        System.out.print("Please, input count of edge: ");
        int countOfEdge = in.nextInt();

        System.out.println("Please, input edges:");
        listOfEdge = new int[countOfEdge][2];
        Set<Integer> vertexes = new HashSet<>();
        for (int i = 0; i < countOfEdge; i++) {
            listOfEdge[i][0] = in.nextInt();
            listOfEdge[i][1] = in.nextInt();
            vertexes.add(listOfEdge[i][0]);
            vertexes.add(listOfEdge[i][1]);
        }

        countOfVertex = vertexes.size();
    }

    @Override
    public void drawGraph() {
        init(countOfVertex);
        for (int[] pair : listOfEdge) {
            drawEdge(pair[0], pair[1]);
        }
    }
}
