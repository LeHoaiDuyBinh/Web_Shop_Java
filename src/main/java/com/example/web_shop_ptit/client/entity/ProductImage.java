package com.example.web_shop_ptit.client.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "productimages")
public class ProductImage {

    @Id
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "ordinal_number")
    private String ordinalNumber;

    @ManyToOne
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    private Product product;

    @Column(name = "image", nullable = false)
    private String image;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(String ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

