package com.example.web_shop_ptit.admin.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "productimages")
public class ProductImageManagement {

    @EmbeddedId
    private ProductImageManagementId id;

    @Column(name = "image")
    private String image;
    @Embeddable
    public static class ProductImageManagementId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "product_code")
        private ProductManagement product;

        @Column(name = "ordinal_number")
        private String ordinalNumber;

        // Constructors, getters, and setters


        public ProductManagement getProduct() {
            return product;
        }

        public void setProduct(ProductManagement product) {
            this.product = product;
        }

        public String getOrdinalNumber() {
            return ordinalNumber;
        }

        public void setOrdinalNumber(String ordinalNumber) {
            this.ordinalNumber = ordinalNumber;
        }
    }

    public ProductImageManagementId getId() {
        return id;
    }

    public void setId(ProductImageManagementId id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
