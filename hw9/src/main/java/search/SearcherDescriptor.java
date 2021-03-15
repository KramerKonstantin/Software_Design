package search;

public class SearcherDescriptor {
    private final String host;
    private final String engine;
    private final int port;

    public SearcherDescriptor(String host, String engine, int port) {
        this.host = host;
        this.engine = engine;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getEngine() {
        return engine;
    }

    public String getHost() {
        return host;
    }
}
