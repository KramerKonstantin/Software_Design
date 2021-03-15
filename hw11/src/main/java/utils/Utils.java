package utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {
    public static LocalDate localDate(Clock clock) {
        return LocalDate.ofInstant(clock.instant(), clock.getZone());
    }

    public static LocalDateTime localDateTime(Clock clock) {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }
}
