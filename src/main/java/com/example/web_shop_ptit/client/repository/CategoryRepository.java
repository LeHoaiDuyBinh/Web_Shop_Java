package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Product findProductByCategoryId(int id);
    Category findByName(String slug);

    List<Product> findByParentCategoryId(int parentCategoryId);

}
