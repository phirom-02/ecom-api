package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.product.AddProductDto;
import com.firom.ecom_api.dto.product.UpdateProductDto;
import com.firom.ecom_api.exception.ResourceNotFoundException;
import com.firom.ecom_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1L, "Laptop", "High-end gaming laptop", 1200.00),
                new Product(2L, "Headphones", "Noise-cancelling headphones", 200.00),
                new Product(3L, "Smartphone", "Latest Android smartphone", 800.00)
        ));

    public List<Product> getAllProducts() {
        return this.products;
    }

    public Product getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No product found with ID " + id));
    }

    public Product addProduct(AddProductDto addProductPayload) {
        var newProduct = new Product();
        newProduct.setId(products.toArray().length);
        newProduct.setName(addProductPayload.name());
        newProduct.setDescription(addProductPayload.description());
        newProduct.setPrice(addProductPayload.price());

        products.add(newProduct);

        return newProduct;
    }

    public Product updateProduct(Long id, UpdateProductDto updatePayload) {
        Product product = this.getProductById(id);

        updatePayload.name().ifPresent(product::setName);
        updatePayload.description().ifPresent(product::setDescription);
        updatePayload.price().ifPresent(product::setPrice);

        return product;
    }

    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId() == id);
    }
}
