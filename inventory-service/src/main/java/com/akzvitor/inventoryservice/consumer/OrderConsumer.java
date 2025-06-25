package com.akzvitor.inventoryservice.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.akzvitor.inventoryservice.model.Order;
import com.akzvitor.inventoryservice.model.OrderItem;
import com.akzvitor.inventoryservice.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderConsumer {

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "orders", groupId = "inventory-group")
    public void handleOrder(String orderJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode orderNode = mapper.readTree(orderJson);
            
            Order order = createOrderFromJson(orderNode);
            
            boolean success = inventoryService.processOrder(order);
            
            if (success) {
                System.out.println("Pedido processado com sucesso: " + order.getId());
            } else {
                System.out.println("Falha ao processar pedido: " + order.getId());
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao processar pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Order createOrderFromJson(JsonNode orderNode) {
        Order order = new Order();
        
        if (orderNode.has("id") && !orderNode.get("id").isNull()) {
            String idString = orderNode.get("id").asText();
            order.setId(UUID.fromString(idString));
        }
        
        List<OrderItem> items = new ArrayList<>();
        JsonNode itemsNode = orderNode.get("items");
        
        if (itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                OrderItem item = new OrderItem();
                item.setProductCode(itemNode.get("productCode").asText());
                item.setQuantity(itemNode.get("quantity").asInt());
                items.add(item);
            }
        }
        
        order.setItems(items);
        return order;
    }
}