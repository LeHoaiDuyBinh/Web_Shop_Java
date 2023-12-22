package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    public List<Category> listAll() {
        return (List<Category>) repo.findAll();
    }

    public Category findByName(String slug) {
        return repo.findByName(slug);
    }

    public List<Product> findProductsByCategory(int category) {
        return repo.findByParentCategoryId(category);
    }
}
