package com.akzvitor.notificationservice.listener;

import com.akzvitor.notificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "inventory-events", groupId = "notification-group")
    public void handleInventoryEvent(String eventJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode eventNode = mapper.readTree(eventJson);
            
            String orderId = eventNode.get("orderId").asText();
            String eventType = eventNode.get("eventType").asText();
            String details = eventNode.get("details").asText();
            String timestamp = eventNode.get("timestamp").asText();
            
            notificationService.processInventoryEvent(orderId, eventType, details, timestamp);
            
        } catch (Exception e) {
            System.err.println("Erro ao processar evento de inventário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}