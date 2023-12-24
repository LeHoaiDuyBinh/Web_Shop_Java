package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSizes, String> {
    List<ProductSizes> findByProductCode(String productCode);
}
