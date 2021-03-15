package storage;

import java.time.LocalDate;

public class ExtensionAccount extends Event {
    public ExtensionAccount(LocalDate date, long days) {
        super();
        description.put(DATE, date);
        description.put(PERIOD, days);
    }
}
