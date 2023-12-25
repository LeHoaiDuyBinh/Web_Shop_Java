package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "color", length = 10)
    private String color;

    @Column(name = "update_latest")
    private Date updateLatest;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Collection<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private Collection<OrderItem> orderItems;


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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(Collection<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
