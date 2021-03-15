package storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {
    private final int id;
    private final LocalDate created;
    private String name;
    private LocalDate expiration;
    private LocalDateTime lastVisit;

    public Account(int id, String name, LocalDate created) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.expiration = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }
}
