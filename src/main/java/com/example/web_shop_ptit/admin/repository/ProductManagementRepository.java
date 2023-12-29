package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.ProductManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManagementRepository extends JpaRepository<ProductManagement, String> {
    ProductManagement findByProductCode(String productCode);
}
