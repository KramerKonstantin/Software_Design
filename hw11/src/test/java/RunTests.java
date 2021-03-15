import exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import services.ManagerService;
import services.ReportService;
import services.TurnstileService;
import storage.Account;
import storage.EventStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RunTests {
    private LocalDateTime now;
    private TestClock clock;
    private EventStorage eventStorage;
    private ManagerService manager;


    @BeforeEach
    void init() {
        now = LocalDateTime.now();
        clock = new TestClock(now);
        eventStorage = new EventStorage();
        manager = new ManagerService(eventStorage, clock);
    }

    @Test
    @DisplayName("Manager service test")
    public void managerServiceTest() throws CreationException, ExtensionException, CollectionException {
        manager.createAccount(1, "Kostya");
        manager.extendAccount(1, 30);
        clock.plusHours(25);

        Account account = manager.collectInfo(1);
        assertEquals("Kostya", account.getName());
        assertEquals(now.toLocalDate(), account.getCreated());
        assertEquals(now.toLocalDate().plusDays(30), account.getExpiration());
    }

    @Test
    @DisplayName("Report serice test")
    public void reportServiceTest() throws CreationException, ExtensionException, CollectionException, EntryException, ExitException {
        TurnstileService turnstile = new TurnstileService(eventStorage, clock);
        ReportService report = new ReportService();

        manager.createAccount(1, "Kostya");
        manager.extendAccount(1, 30);
        turnstile.comeIn(1);
        clock.plusHours(2);
        turnstile.goOut(1);
        clock.plusHours(23);
        turnstile.comeIn(1);
        clock.plusHours(1);
        turnstile.goOut(1);

        eventStorage.subscribe(report);
        assertEquals(1.5, report.meanDuration(), 1e-8);
        assertEquals(1., report.meanFrequency(), 1e-8);

        Map<LocalDate, Map.Entry<Integer, Long>> daily = report.dailyStatistics();
        assertTrue(daily.containsKey(now.toLocalDate()));
        assertTrue(daily.containsKey(now.plusDays(1).toLocalDate()));
        assertEquals(daily.get(now.toLocalDate()), (new AbstractMap.SimpleEntry<>(1, 2L)));
    }

    @Test
    @DisplayName("Turnstile service test")
    public void turnstileServiceTest() throws CreationException, CollectionException, EntryException, ExtensionException, ExitException {
        TurnstileService turnstile = new TurnstileService(eventStorage, clock);

        manager.createAccount(1, "Kostya");
        manager.extendAccount(1, 2);
        turnstile.comeIn(1);
        clock.plusHours(25);
        turnstile.goOut(1);

        Account account = manager.collectInfo(1);
        assertEquals(now, account.getLastVisit());
    }
}
