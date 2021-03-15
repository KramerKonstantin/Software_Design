package storage;

import java.util.HashMap;
import java.util.Map;

public abstract class Event {
    protected Map<String, Object> description = new HashMap<>();

    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NAME = "name";
    public static final String PERIOD = "days";

    public Object get(String key) {
        return description.get(key);
    }
}
