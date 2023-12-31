package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Products")
public class ProductManagement {
    @Id
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryManagement category;

    @Column(name = "color")
    private String color;
    @Column(name = "update_latest")
    private Date updateLatest;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL)
    private List<ProductImageManagement> productImages;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL)
    private List<ProductSizeManagement> productSizes;

    public List<OrderItemsManagement> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsManagement> orderItems) {
        this.orderItems = orderItems;
    }

    @OneToMany(mappedBy = "product")
    private List<OrderItemsManagement> orderItems;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CategoryManagement getCategory() {
        return category;
    }

    public void setCategory(CategoryManagement category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getUpdateLatest() {
        return updateLatest;
    }

    public void setUpdateLatest(Date updateLatest) {
        this.updateLatest = updateLatest;
    }

    public List<ProductImageManagement> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageManagement> productImages) {
        this.productImages = productImages;
    }

    public List<ProductSizeManagement> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSizeManagement> productSizes) {
        this.productSizes = productSizes;
    }

    public Long calculateTotalQuantity() {
        Long totalQuantity = 0L;
        if (productSizes != null) {
            for (ProductSizeManagement size : productSizes) {
                totalQuantity += size.getQuantity();
            }
        }
        return totalQuantity;
    }
}
