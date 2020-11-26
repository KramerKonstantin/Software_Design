import application.AwtApplication;
import application.FxApplication;
import application.GraphApplication;
import graph.GraphType;
import graph.TypeGraph;

public class Main {
    public static void main(String[] args ) {
        if (args.length != 2) {
            throw new IllegalArgumentException("There must be two arguments.");
        }

        GraphApplication graphApplication = switch (args[0]) {
            case "fx" -> new FxApplication();
            case "awt" -> new AwtApplication();
            default -> throw new IllegalArgumentException("First argument must be \"fx\" or \"awt\"");
        };

        TypeGraph.graph = switch (args[1]) {
            case "matrix" -> GraphType.MATRIX;
            case "list" -> GraphType.LIST;
            default -> throw new IllegalArgumentException("Second argument must be \"matrix\" or \"list\"");
        };

        graphApplication.startApplication();
    }
}