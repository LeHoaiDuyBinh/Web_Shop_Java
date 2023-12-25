package com.example.web_shop_ptit.admin.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Categories")
public class CategoryManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private CategoryManagement parentCategoryManagement;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CategoryManagement getParentCategoryManagement() {
        return parentCategoryManagement;
    }

    public void setParentCategoryManagement(CategoryManagement parentCategoryManagement) {
        this.parentCategoryManagement = parentCategoryManagement;
    }
}

