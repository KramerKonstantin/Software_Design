package actors.results;

import java.util.HashMap;


public class MasterActorResult {
    private final HashMap<String, String> result;

    public MasterActorResult() {
        this.result = new HashMap<>();
    }

    public HashMap<String, String> getResult() {
        return result;
    }
}
