package actors.results;

import search.SearcherDescriptor;

public class ChildActorResult {
    private final String response;
    private final SearcherDescriptor info;

    public ChildActorResult(String response, SearcherDescriptor info) {
        this.response = response;
        this.info = info;
    }

    public String getResponse() {
        return response;
    }

    public SearcherDescriptor getInfo() {
        return info;
    }
}
