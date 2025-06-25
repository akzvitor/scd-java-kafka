package com.akzvitor.orderservice.controller;

import com.akzvitor.orderservice.model.Order;
import com.akzvitor.orderservice.model.OrderItem;
import com.akzvitor.orderservice.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderProducer producer;

    @PostMapping
    public String createOrder(@RequestBody List<OrderItem> items) {
        Order order = new Order(UUID.randomUUID(), Instant.now(), items);
        producer.sendOrder(order);
        return "Pedido enviado com sucesso: " + order.getId();
    }
}