package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductCode(String id);
}
