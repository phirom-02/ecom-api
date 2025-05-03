package com.firom.ecom_api.module.product;

import com.firom.ecom_api.module.product.dto.CreateProductDto;
import com.firom.ecom_api.module.product.dto.ProductDto;
import com.firom.ecom_api.module.product.dto.UpdateProductDto;
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
