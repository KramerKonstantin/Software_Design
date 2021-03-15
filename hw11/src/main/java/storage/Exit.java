package storage;

import java.time.LocalDateTime;

public class Exit extends Event {
    public Exit(LocalDateTime time) {
        super();
        description.put(TIME, time);
    }
}
