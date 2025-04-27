package com.firom.ecom_api.repository;

import com.firom.ecom_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p")
    ArrayList<Product> findAllProduct();

    Product findFirstById(Integer id);
}
