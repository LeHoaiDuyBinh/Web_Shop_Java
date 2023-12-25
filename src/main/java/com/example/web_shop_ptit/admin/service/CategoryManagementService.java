package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.repository.CategoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryManagementService {
    @Autowired
    private CategoryManagementRepository repo;

    public List<CategoryManagement> listAll() {
        return (List<CategoryManagement>) repo.findAll();
    }

    public void saveCategory(String tenDanhMuc, CategoryManagement danhMucCha) {
        CategoryManagement category = new CategoryManagement();
        category.setName(tenDanhMuc);
        category.setParentCategoryManagement(danhMucCha);

        repo.save(category);
    }
    public CategoryManagement getParentCategoryById(int categoryParentId){
        return repo.findByCategoryId(categoryParentId);
    }
    public void updateCategory(int categoryId, String name, CategoryManagement danhMucCha) {
        CategoryManagement category = repo.findByCategoryId(categoryId);

        if (category != null) {
            category.setName(name);
            category.setParentCategoryManagement(danhMucCha);
            repo.save(category);
        }
    }
    public void deleteCategory(int categoryParentId) {
        repo.deleteById(categoryParentId);
    }

}
