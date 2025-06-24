package com.akzvitor.orderservice.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private Instant timestamp;
    private List<String> items;

    public Order() { }

    public Order(UUID id, Instant timestamp, List<String> items) {
        this.id = id;
        this.timestamp = timestamp;
        this.items = items;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}