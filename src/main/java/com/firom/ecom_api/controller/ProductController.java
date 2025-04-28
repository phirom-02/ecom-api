package com.firom.ecom_api.controller;

import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.dto.product.CreateProductDto;
import com.firom.ecom_api.dto.product.ProductDto;
import com.firom.ecom_api.dto.product.UpdateProductDto;
import com.firom.ecom_api.handler.ApiResponseHandler;
import com.firom.ecom_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProducts() {
        return ApiResponseHandler.success(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Integer id) {
        return ApiResponseHandler.success(productService.getProductById(id));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody CreateProductDto createProductDto) {
        return ApiResponseHandler.created(productService.createProduct(createProductDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Integer id, @RequestBody UpdateProductDto updateProductDto) {
        return ApiResponseHandler.success(productService.updateProduct(id, updateProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@PathVariable Integer id) {
        return ApiResponseHandler.success(productService.deleteProduct(id));
    }
}
