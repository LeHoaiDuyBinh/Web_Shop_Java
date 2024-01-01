package com.example.web_shop_ptit.admin.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Categories")
public class CategoryManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;
    @NotBlank(message = "Vui lòng nhập tên danh mục!")
    @Size(max = 50, message = "Tên danh mục không vượt quá 50 ký tự")
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