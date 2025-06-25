package com.akzvitor.inventoryservice.service;

import com.akzvitor.inventoryservice.model.Order;
import com.akzvitor.inventoryservice.model.OrderItem;
import com.akzvitor.inventoryservice.model.Product;
import com.akzvitor.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean processOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            Optional<Product> optionalProduct = productRepository.findById(item.getProductCode());
            if (optionalProduct.isEmpty()) {
                System.out.println("Produto não encontrado: " + item.getProductCode());
                return false;
            }

            Product product = optionalProduct.get();
            if (product.getQuantity() < item.getQuantity()) {
                System.out.println("Estoque insuficiente para: " + product.getCode());
                return false;
            }
        }

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductCode()).get();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        return true;
    }
}