package com.firom.ecom_api.mapper;

import com.firom.ecom_api.dto.product.CreateProductDto;
import com.firom.ecom_api.dto.product.ProductDto;
import com.firom.ecom_api.dto.product.UpdateProductDto;
import com.firom.ecom_api.model.Product;

public class ProductMapper {

    public static Product productDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(product.getId());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }

    public static Product createProductDTOtoEntity(CreateProductDto saveProductDto) {
        Product product = new Product();
        product.setName(saveProductDto.name());
        product.setDescription(saveProductDto.description());
        product.setPrice(saveProductDto.price());
        return product;
    }

    public static Product updateProductDTOtoEntity(Integer id, UpdateProductDto updateProductDto, Product product) {
        product.setId(id);
        updateProductDto.name().ifPresent(product::setName);
        updateProductDto.description().ifPresent(product::setDescription);
        updateProductDto.price().ifPresent(product::setPrice);

        return product;
    }

    public static ProductDto toProductDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
