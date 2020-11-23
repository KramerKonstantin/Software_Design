import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface EventsStatistic {
    void incEvent(String name);

    long getEventStatisticByName(String name);

    Map<Instant, List<String>> getAllEventStatistic();

    void printStatistic();
}
