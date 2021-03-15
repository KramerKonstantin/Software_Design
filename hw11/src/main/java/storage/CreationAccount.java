package storage;

import java.time.LocalDate;

public class CreationAccount extends Event {
    public CreationAccount(LocalDate date, String name) {
        super();
        description.put(DATE, date);
        description.put(NAME, name);
    }
}
