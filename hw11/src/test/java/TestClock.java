import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestClock extends Clock {
    private Instant now;

    public TestClock(LocalDateTime time) {
        super();
        now = time.atZone(ZoneId.systemDefault()).toInstant();
    }

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instant instant() {
        return now;
    }

    public void plusHours(long hours) {
        now = now.plusSeconds(hours * 3600L);
    }
}
