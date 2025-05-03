package com.firom.ecom_api.module.product;

import com.firom.ecom_api.common.dto.ApiResponse;
import com.firom.ecom_api.module.product.dto.CreateProductDto;
import com.firom.ecom_api.module.product.dto.ProductDto;
import com.firom.ecom_api.module.product.dto.UpdateProductDto;
import com.firom.ecom_api.handler.ApiResponseHandler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody @Valid CreateProductDto createProductDto) {
        return ApiResponseHandler.created(productService.createProduct(createProductDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Integer id, @RequestBody @Valid UpdateProductDto updateProductDto) {
        return ApiResponseHandler.success(productService.updateProduct(id, updateProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> deleteProduct(@PathVariable Integer id) {
        return ApiResponseHandler.success(productService.deleteProduct(id));
    }
}
