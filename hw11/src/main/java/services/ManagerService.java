package services;

import exceptions.CollectionException;
import exceptions.CreationException;
import exceptions.ExtensionException;
import storage.*;
import utils.Utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ManagerService {
    private final EventStorage storage;
    private final Clock clock;

    public ManagerService(EventStorage storage, Clock clock) {
        this.storage = storage;
        this.clock = clock;
    }

    public void extendAccount(int id, long days) throws ExtensionException {
        if (!storage.isExist(id)) {
            throw new ExtensionException("This id = " + id + " doesn't exit.");
        }

        storage.addEvent(id, new ExtensionAccount(Utils.localDate(clock), days));
    }

    public void createAccount(int id, String name) throws CreationException {
        if (storage.isExist(id)) {
            throw new CreationException("This id = " + id + " already exists.");
        }

        storage.addEvent(id, new CreationAccount(Utils.localDate(clock), name));
    }

    public Account collectInfo(int id) throws CollectionException {
        List<Event> events = storage.getEvents(id);

        if (events == null) {
            throw new CollectionException("This id = " + id + " doesn't exist.");
        }

        if (!(events.get(0) instanceof CreationAccount)) {
            throw new CollectionException("This id = " + id + " wasn't created.");
        }

        LocalDate created = (LocalDate) events.get(0).get(Event.DATE);
        String name = (String) events.get(0).get(Event.NAME);
        Account account = new Account(id, name, created);

        for (int i = 1; i < events.size(); i++) {
            Event event = events.get(i);

            if (event instanceof ExtensionAccount) {
                LocalDate date = (LocalDate) event.get(Event.DATE);
                Long period = (Long) event.get(Event.PERIOD);
                LocalDate expiration = date.plusDays(period);

                account.setExpiration(expiration);
            } else if (event instanceof Entry) {
                LocalDateTime time = (LocalDateTime) event.get(Event.TIME);

                account.setLastVisit(time);
            }
        }

        return account;
    }
}
