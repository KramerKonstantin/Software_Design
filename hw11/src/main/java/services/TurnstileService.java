package services;

import exceptions.CollectionException;
import exceptions.EntryException;
import exceptions.ExitException;
import storage.*;
import utils.Utils;

import java.time.Clock;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class TurnstileService {
    private final EventStorage storage;
    private final Clock clock;

    public TurnstileService(EventStorage storage, Clock clock) {
        this.storage = storage;
        this.clock = clock;
    }

    public void comeIn(int id) throws EntryException, CollectionException {
        if(!mayComeIn(id)) {
            throw new EntryException("This id = " + id + " can't come in.");
        }

        storage.addEvent(id, new Entry(Utils.localDateTime(clock)));
    }

    public void goOut(int id) throws ExitException, CollectionException {
        if (!mayGoOut(id)) {
            throw new ExitException("This id = " + id + " can't go out.");
        }

        storage.addEvent(id, new Exit(Utils.localDateTime(clock)));
    }

    private boolean mayComeIn(int id) throws CollectionException {
        Map.Entry<LocalDate, Boolean> p = getExpirationAndInside(id);

        return !p.getValue() && p.getKey().isAfter(Utils.localDate(clock));
    }

    private boolean mayGoOut(int id) throws CollectionException {
        Map.Entry<LocalDate, Boolean> p = getExpirationAndInside(id);

        return p.getValue();
    }

    private Map.Entry<LocalDate, Boolean> getExpirationAndInside(int id) throws CollectionException {
        List<Event> events = storage.getEvents(id);

        if (events == null) {
            throw new CollectionException("This id = " + id + " doesn't exist.");
        }

        Event event0 = events.get(0);

        if (!(event0 instanceof CreationAccount)) {
            throw new CollectionException("This id = " + id + " wasn't created.");
        }

        LocalDate expiration = (LocalDate) event0.get(Event.DATE);
        boolean inside = false;

        for (int i = 1; i < events.size(); i++) {
            Event event = events.get(i);
            if (event instanceof ExtensionAccount) {
                LocalDate date = (LocalDate) event.get(Event.DATE);
                Long period = (Long) event.get(Event.PERIOD);
                expiration = date.plusDays(period);
            } else if (event instanceof Entry) {
                inside = true;
            } else if (event instanceof Exit) {
                inside = false;
            }
        }

        return new AbstractMap.SimpleEntry<>(expiration, inside);
    }


}
