package com.firom.ecom_api.service;

import com.firom.ecom_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> getAllProducts() {
        return List.of(
                new Product(1L, "Laptop", "High-end gaming laptop", 1200.00),
                new Product(2L, "Headphones", "Noise-cancelling headphones", 200.00),
                new Product(3L, "Smartphone", "Latest Android smartphone", 800.00)
        );
    }
}
