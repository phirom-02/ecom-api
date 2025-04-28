package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.product.CreateProductDto;
import com.firom.ecom_api.dto.product.ProductDto;
import com.firom.ecom_api.dto.product.UpdateProductDto;
import com.firom.ecom_api.common.exceptions.ResourceNotFoundException;
import com.firom.ecom_api.common.enums.ErrorCode;
import com.firom.ecom_api.mapper.ProductMapper;
import com.firom.ecom_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        var productList = productRepository.findAllProduct();
        return productList.stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Integer id) {
        var productDB = productRepository.findFirstById(id);
        if (productDB == null) {
            throw new ResourceNotFoundException("No product found with ID " + id, ErrorCode.PRODUCT_NOT_FOUND);
        }
        return ProductMapper.toProductDTO(productDB);
    }

    @Override
    public ProductDto createProduct(CreateProductDto createProductDto) {
        var newProduct = ProductMapper.createProductDTOtoEntity(createProductDto);
        var productDB = productRepository.save(newProduct);
        return ProductMapper.toProductDTO(productDB);
    }

    @Override
    public ProductDto updateProduct(Integer id, UpdateProductDto updateProductDto) {
        var productDB = getProductById(id);
        var updatedProduct = ProductMapper.updateProductDTOtoEntity(
                updateProductDto,
                ProductMapper.productDtoToEntity(productDB)
        );
        var product = productRepository.save(updatedProduct);
        return ProductMapper.toProductDTO(product);
    }

    @Override
    public ProductDto deleteProduct(Integer id) {
        var productDB = getProductById(id);
        productRepository.deleteById(productDB.id());
        return productDB;
    }
}

