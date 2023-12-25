package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryManagementRepository extends JpaRepository<CategoryManagement, Integer> {
    CategoryManagement findByCategoryId(int categoryId);
}

