package com.akzvitor.orderservice.controller;

import com.akzvitor.orderservice.kafka.OrderProducer;
import com.akzvitor.orderservice.model.Order;
import com.akzvitor.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderProducer orderProducer, OrderRepository orderRepository) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public String createOrder(@RequestBody Order orderRequest) {
        Order order = new Order(
            UUID.randomUUID(),
            Instant.now(),
            orderRequest.getItems()
        );

        orderRepository.save(order);
        orderProducer.sendOrder(order);

        return "Pedido criado e enviado com sucesso: " + order.getId();
    }
}