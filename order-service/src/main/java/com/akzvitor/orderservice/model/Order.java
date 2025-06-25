package com.akzvitor.orderservice.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private Instant timestamp;
    private List<OrderItem> items;

    public Order() {
    }

    public Order(UUID id, Instant timestamp, List<OrderItem> items) {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}