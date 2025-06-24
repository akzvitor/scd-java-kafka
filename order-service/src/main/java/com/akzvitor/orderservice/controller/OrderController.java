package com.akzvitor.orderservice.controller;

import com.akzvitor.orderservice.kafka.OrderProducer;
import com.akzvitor.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    @Autowired
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public String createOrder(@RequestBody Order orderRequest) {
        Order order = new Order(
            UUID.randomUUID(),
            Instant.now(),
            orderRequest.getItems()
        );

        orderProducer.sendOrder(order);
        return "Pedido enviado com sucesso: " + order.getId();
    }
}