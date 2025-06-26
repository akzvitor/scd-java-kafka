package com.akzvitor.notificationservice.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void processInventoryEvent(String orderId, String eventType, String details, String timestamp) {
        switch (eventType) {
            case "ORDER_PROCESSED_SUCCESS":
                sendSuccessNotification(orderId, details, timestamp);
                break;
            case "INSUFFICIENT_STOCK":
                sendStockAlertNotification(orderId, details, timestamp);
                break;
            case "PRODUCT_NOT_FOUND":
                sendProductNotFoundNotification(orderId, details, timestamp);
                break;
            case "ORDER_PROCESSING_ERROR":
                sendErrorNotification(orderId, details, timestamp);
                break;
            default:
                sendGenericNotification(orderId, eventType, details, timestamp);
        }
    }
    
    private void sendSuccessNotification(String orderId, String details, String timestamp) {
        System.out.println("\n\nEmail (alerta - isto é uma simulação)");
        System.out.println("Para: cliente@email.com");
        System.out.println("Assunto: Pedido secreto - Sucesso");
        System.out.println("Pedido ID: " + orderId);
        System.out.println("Detalhes: " + details);
        System.out.println("Data/Hora: " + timestamp);
        System.out.println("Mensagem: Seu pedido foi processado e os itens secretos foram reservados no estoque.");
        System.out.println("\n\nSMS ENVIADO");
        System.out.println("Para: +55 (64) 7070-7070");
        System.out.println("Mensagem: Pedido " + orderId.substring(0, 8) + "... processado com sucesso!");
    }
    
    private void sendStockAlertNotification(String orderId, String details, String timestamp) {
        System.out.println("\n\nEmail (alerta - isto é uma simulação)");
        System.out.println("Para: cliente@email.com");
        System.out.println("Assunto: Pedido secreto - Estoque insuficiente");
        System.out.println("Mensagem: Infelizmente não conseguimos processar seu pedido devido ao estoque insuficiente. Isso mesmo. Acabou.");
    }
    
    private void sendProductNotFoundNotification(String orderId, String details, String timestamp) {
        System.out.println("\n\nEmail (alerta - isto é uma simulação)");
        System.out.println("Para: cleinte@email.com");
        System.out.println("Assunto: Pedido secreto - Produto não encontrado no sistema");
        System.out.println("Pedido ID: " + orderId);
        System.out.println("Detalhes: " + details);
        System.out.println("Data/Hora: " + timestamp);
        System.out.println("Ação: Verificar cadastro de produtos no sistema.");
    }
    
    private void sendErrorNotification(String orderId, String details, String timestamp) {
        System.out.println("\n\nEmail (alerta - isto é uma simulação)");
        System.out.println("Para: dev@empresa.com, admin@empresa.com");
        System.out.println("Assunto: Erro no pedido secreto - Falha no processamento");
        System.out.println("Pedido ID: " + orderId);
        System.out.println("Detalhes: " + details);
        System.out.println("Data/Hora: " + timestamp);
        System.out.println("Prioridade: alta");
    }
    
    private void sendGenericNotification(String orderId, String eventType, String details, String timestamp) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("\n\n Notificação genérica" + eventType +"(alerta - isto é uma simulação)");
        System.out.println("Pedido ID: " + orderId);
        System.out.println("Tipo do Evento: " + eventType);
        System.out.println("Detalhes: " + details);
        System.out.println("Data/Hora: " + timestamp);
    }
}