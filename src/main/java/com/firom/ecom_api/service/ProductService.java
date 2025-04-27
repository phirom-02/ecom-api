package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.product.CreateProductDto;
import com.firom.ecom_api.dto.product.ProductDto;
import com.firom.ecom_api.dto.product.UpdateProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Integer id);

    ProductDto createProduct(CreateProductDto createProductDto);

    ProductDto updateProduct(Integer id, UpdateProductDto updateProductDto);

    ProductDto deleteProduct(Integer id);
}
