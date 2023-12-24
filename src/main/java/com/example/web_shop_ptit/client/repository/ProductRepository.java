package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductCode(String id);
    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.productCode <> :productCode")
    List<Product> findSimilarProducts(@Param("categoryId") long categoryId, @Param("productCode") String productCode);
}
