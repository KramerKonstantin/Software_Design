package storage;

import java.time.LocalDateTime;

public class Entry extends Event {
    public Entry(LocalDateTime time) {
        super();
        description.put(TIME, time);
    }
}
