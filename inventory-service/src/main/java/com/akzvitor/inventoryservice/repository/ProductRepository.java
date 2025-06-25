package com.akzvitor.inventoryservice.repository;

import com.akzvitor.inventoryservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}