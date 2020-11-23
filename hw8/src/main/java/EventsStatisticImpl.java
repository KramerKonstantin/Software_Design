import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {
    private final Map<String, List<Instant>> events;
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
        this.events = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new ArrayList<>());
        }

        events.get(name).add(clock.instant());
    }

    @Override
    public long getEventStatisticByName(String name) {
        removeOldStatistic();

        if (!events.containsKey(name)) {
            return 0;
        }

        return events.get(name).size();
    }

    @Override
    public Map<Instant, List<String>> getAllEventStatistic() {
        removeOldStatistic();

        Map<Instant, List<String>> allEventStatistic = new TreeMap<>();
        for (String name: events.keySet()) {
            List<Instant> instants = events.get(name);

            for (Instant instant: instants) {
                if (!allEventStatistic.containsKey(instant)) {
                    allEventStatistic.put(instant, new ArrayList<>());
                }

                allEventStatistic.get(instant).add(name);
            }
        }

        return allEventStatistic;
    }

    @Override
    public void printStatistic() {
        Map<Instant, List<String>> statistic = getAllEventStatistic();

        statistic.forEach((time, name) ->
                System.out.println("Time: " + time + ". Events: " + String.join(", ", name))
        );
    }

    private void removeOldStatistic() {
        Instant hourAgo = clock.instant().minus(1L, ChronoUnit.HOURS);

        for (String name : events.keySet()) {
            List<Instant> instants = events.get(name).stream()
                    .filter(instant -> instant.isAfter(hourAgo))
                    .collect(Collectors.toList());

            events.put(name, instants);
        }

        events.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}