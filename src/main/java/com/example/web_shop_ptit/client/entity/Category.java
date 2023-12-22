package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

import java.text.Normalizer;
import java.util.Collection;
import java.util.regex.Pattern;

@Entity
@Table(name = "Categories")
public class Category  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private Collection<Product> products;

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

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Transient
    public String getSlug() {
        return createSlug(removeDiacritics(this.name));
    }

    private String removeDiacritics(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private String createSlug(String input) {
        return input.toLowerCase().replaceAll("\\s", "-");
    }
}
