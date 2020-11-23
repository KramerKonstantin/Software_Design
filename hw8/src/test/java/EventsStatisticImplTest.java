import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EventsStatisticImplTest {
    private AdaptableClock clock;
    private EventsStatistic eventsStatistic;

    @BeforeEach
    void setup() {
        clock = new AdaptableClock(Instant.now());
        eventsStatistic = new EventsStatisticImpl(clock);
    }

    @Test
    void testStatisticByNonExistingName() {
        assertThat(eventsStatistic.getEventStatisticByName("Event")).isZero();
    }

    @Test
    void testStatisticByName() {
        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event Second");

        assertThat(eventsStatistic.getEventStatisticByName("Event First")).isEqualTo(2);
    }

    @Test
    void testOldEvent() {
        eventsStatistic.incEvent("Event");
        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        assertThat(eventsStatistic.getEventStatisticByName("Event")).isZero();
    }

    @Test
    void testStatisticByNameAfterHourTest() {
        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event Second");
        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        eventsStatistic.incEvent("Event First");

        assertThat(eventsStatistic.getEventStatisticByName("Event First")).isEqualTo(1);
    }

    @Test
    void testAllStatistic() {
        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event Second");

        Instant time1 = clock.instant();
        clock.setNow(Instant.now().plus(30, ChronoUnit.MINUTES));

        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event Second");
        eventsStatistic.incEvent("Event Second");
        eventsStatistic.incEvent("Event Second");

        Instant time2 = clock.instant();
        clock.setNow(Instant.now().plus(30, ChronoUnit.MINUTES));

        Instant time3 = clock.instant();
        eventsStatistic.incEvent("Event Third");

        Map<Instant, List<String>> allEventStatistic = eventsStatistic.getAllEventStatistic();
        assertThat(allEventStatistic).containsEntry(time1, List.of("Event First", "Event Second"));
        assertThat(allEventStatistic).containsEntry(time2, List.of("Event First", "Event Second", "Event Second", "Event Second"));
        assertThat(allEventStatistic).containsEntry(time3, List.of("Event Third"));
    }

    @Test
    void testAllStatisticAfterHour() {
        eventsStatistic.incEvent("Event First");
        eventsStatistic.incEvent("Event Second");

        clock.setNow(Instant.now().plus(1, ChronoUnit.HOURS));

        eventsStatistic.incEvent("Event Third");

        Map<Instant, List<String>> allEventStatistic = eventsStatistic.getAllEventStatistic();
        assertThat(allEventStatistic).containsEntry(clock.instant(), List.of("Event Third"));
    }
}