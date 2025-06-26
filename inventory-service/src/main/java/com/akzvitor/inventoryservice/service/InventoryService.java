package com.akzvitor.inventoryservice.service;

import com.akzvitor.inventoryservice.model.Order;
import com.akzvitor.inventoryservice.model.OrderItem;
import com.akzvitor.inventoryservice.model.Product;
import com.akzvitor.inventoryservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public InventoryService(ProductRepository productRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public boolean processOrder(Order order) {
        boolean success = false;
        String details = "";
        
        try {
            for (OrderItem item : order.getItems()) {
                Optional<Product> optionalProduct = productRepository.findById(item.getProductCode());
                if (optionalProduct.isEmpty()) {
                    details = "Produto não encontrado: " + item.getProductCode();
                    System.out.println(details);
                    publishInventoryEvent(order.getId().toString(), "PRODUCT_NOT_FOUND", details);
                    return false;
                }

                Product product = optionalProduct.get();
                if (product.getQuantity() < item.getQuantity()) {
                    details = "Estoque insuficiente para: " + product.getCode() + 
                             " (Disponível: " + product.getQuantity() + ", Solicitado: " + item.getQuantity() + ")";
                    System.out.println(details);
                    publishInventoryEvent(order.getId().toString(), "INSUFFICIENT_STOCK", details);
                    return false;
                }
            }

            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProductCode()).get();
                int oldQuantity = product.getQuantity();
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.save(product);
                
                String itemDetails = "Produto: " + product.getCode() + 
                                   ", Quantidade anterior: " + oldQuantity + 
                                   ", Quantidade atual: " + product.getQuantity();
                System.out.println("Estoque atualizado - " + itemDetails);
            }

            success = true;
            details = "Pedido processado com sucesso. Itens: " + order.getItems().size();
            publishInventoryEvent(order.getId().toString(), "ORDER_PROCESSED_SUCCESS", details);
            
        } catch (Exception e) {
            details = "Erro ao processar pedido: " + e.getMessage();
            System.out.println(details);
            publishInventoryEvent(order.getId().toString(), "ORDER_PROCESSING_ERROR", details);
        }

        return success;
    }
    
    private void publishInventoryEvent(String orderId, String eventType, String details) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("orderId", orderId);
            event.put("eventType", eventType);
            event.put("details", details);
            event.put("timestamp", LocalDateTime.now().toString());
            event.put("service", "inventory-service");
            
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("inventory-events", orderId, eventJson);
            
            System.out.println("Evento publicado: " + eventType + " para pedido: " + orderId);
            
        } catch (Exception e) {
            System.err.println("Erro ao publicar evento: " + e.getMessage());
        }
    }
}